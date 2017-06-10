package com.smpp.platform.dal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;


public class JedisEntityManager<T> {
	private Class<T> type;
	private String typeName;
	private JedisPersistenceUnit pu;
	Map<String,Property> properties=new HashMap<String, Property>();
	Property idProp;
	List<Property> uniqueProps=new ArrayList<Property>();
	
	public JedisEntityManager(String typeName,Class<T> type,JedisPersistenceUnit pu){
		this.pu=pu;
		this.typeName=typeName;
		this.type=type;
		for(Field field:type.getDeclaredFields()){
			if(!Modifier.isStatic(field.getModifiers())){
			Property pp=new Property(field);
			if(pp.isId()){
				if(!pp.getPropertyType().equals(Long.TYPE)){
					throw new IllegalArgumentException("l'id doit etre long pour "+type);
				}
				if(idProp!=null){
					throw new IllegalArgumentException("Je peux pas avoir deux id pour "+type);
				}
				idProp=pp;
			}else if(pp.isUnique()){
				uniqueProps.add(pp);// email case
			}
			properties.put(field.getName(), pp);// type field , field
			}
		}
		if(idProp==null){
			throw new IllegalArgumentException("aucun id trouvé pour "+type);
		}
	} 
	
	public JedisEntityManager(Class<T> type,JedisPersistenceUnit pu){
		this(type.getSimpleName(),type,pu); //string type class , type , pu
		// const(3) in const(2)
	}
	
	
	public long getMax(){
		String s=pu.get(typeName+"Max:");
		if(s==null || s.length()==0){
			return 0;
		}
		return Long.parseLong(s);
	}

	
	private void setMax(long value){
		pu.set(typeName+"Max:",""+value);
	}

	public long incAndGetMax(){
		long v=getMax();
		pu.set(typeName+"Max:",""+(v+1));
		return v;
	}

	public T findById(long id){
		
		
       Map<String, String> properties0 = pu.hgetAll(typeName+":" + id);
		if(properties0==null || properties0.isEmpty()){
			return null;
		}
		try{
			T o=type.newInstance();
			idProp.set(o, id);
			
			for (Property property : properties.values()) {
				String s=properties0.get(property.getName());
				Object v=s;
				if(property.getPropertyType().equals(String.class)){
					v=s;
				}else if(property.getPropertyType().equals(Long.TYPE)){
					v=(s==null || s.trim().length()==0)?0:Long.parseLong(s);
				}else if(property.getPropertyType().equals(Integer.TYPE)){
					v=(s==null || s.trim().length()==0)?0:Integer.parseInt(s);
				}else{
					throw new IllegalArgumentException("Type "+property.getPropertyType()
							+" not supported in property "+property.getName()+" in "+property.getBaseType());
				}
				property.set(o, v);// property.set(user,firstName)
			}
		    return o;
		}catch(RuntimeException ee){
			throw ee;
		}catch(Exception ee){
			throw new RuntimeException("Problem",ee);
		}
		
//	user.setFirstName(properties.get("firstName"));
//	user.setLastName(properties.get("lastName"));
//	user.setGender(properties.get("gender"));
//	user.setEmail(properties.get("email"));
//	user.setRole(properties.get("role"));
//	user.setPassword(properties.get("password"));
//	user.setPhoneNumber(properties.get("phoneNumber"));
//
	}
	
	public void persist(Object o){
		
		long newId=0;
		if(idProp.isGeneratedValue()){
			newId=incAndGetMax();
			idProp.set(o, newId); // new object/or/instence , newInstanceId ---- IdProp = sequence /or/ iterator
		}else{
			newId=(Long)idProp.get(o);
		}
		
		final Map<String, String> userProperties = new HashMap<String, String>();
		for (Property property : properties.values()) {
			Object v=property.get(o);
			String s=v==null?"":String.valueOf(v);
			userProperties.put(property.getName(), s);
		}
		 pu.hmset(typeName+":"+newId, userProperties);

		 for (Property property : uniqueProps) {
			 pu.set("index:"+typeName+":"+property.getName()+":"+property.get(o), ""+newId);
		}
	}
	
	public void merge(Object o){
		long oldId=(Long)idProp.get(o);

		Object oldObject=findById(oldId);
		if(oldObject==null){
			throw new IllegalArgumentException("Never 9h et demi");
		}
		
		for (Property property : uniqueProps) {
			 pu.del("index:"+typeName+":"+property.getName()+":"+property.get(oldObject));
		}

		
		Map<String, String> userProperties = new HashMap<String, String>();
		for (Property property : properties.values()) {
			Object v=property.get(o);
			String s=v==null?"":String.valueOf(v);
			userProperties.put(property.getName(), s);
		}
		 pu.hmset(typeName+":"+oldId, userProperties);		
		for (Property property : uniqueProps) {
			 pu.set("index:"+typeName+":"+property.getName()+":"+property.get(o), ""+oldId);
		}
	}

	public  T findOne(ObjectFilter<T> filter){
		T o;
		long max=getMax();
		for(long i= 1;i<max;i++){
			o = findById(i);
			if(o!=null && (filter==null || filter.accept(o))){
				return (o);
			}
		}
		return null;
	}
	
	public  List<T> findAll(ObjectFilter<T> filter){
		List<T> list = new ArrayList<T>();
		T o;
		long max=getMax();
		for(long i= 0;i<max;i++){
			o = findById(i);
			if(o!=null && (filter==null || filter.accept(o))){
				list.add(o);
			}
		}
		return list;
	}
	
	public  T findByIndexedProperty(String property,String value){
		 String id=pu.get("index:"+typeName+":"+property+":"+value);
		if(id==null || id.length()==0){
			return null;
		}
		return findById(Long.parseLong(id));
	}
	
	public void delete(Object o){

		long oldId=(Long)idProp.get(o);

		Object oldObject=findById(oldId);
		if(oldObject==null){
			throw new IllegalArgumentException("Never 9h et demi");
		}
		
		for (Property property : uniqueProps) {
			 pu.del("index:"+typeName+":"+property.getName()+":"+property.get(oldObject));
		}

		
		 pu.del(typeName+":"+oldId);		

		}
}
