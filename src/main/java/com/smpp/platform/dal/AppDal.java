package com.smpp.platform.dal;

import java.util.List;

import javax.annotation.PostConstruct;

import com.smpp.platform.entities.GroupSMS;
import com.smpp.platform.entities.IndividualSMS;
import com.smpp.platform.entities.Parameters;
import com.smpp.platform.entities.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class AppDal {
   public static final String USERS_MAX="usersMax";
   public static final String SMS_MAX="smsMax";
   public static final String SMS_ALL_MAX="smsAllMax";
	 JedisPersistenceUnit pu; /// This !
	@PostConstruct
	private void init(){
		int port = 8056;
		System.out.println(">>>>Create Jedis connection");
		pu=new JedisPersistenceUnit("localhost",port);
		pu.registerEntity(User.class);
		pu.registerEntity(IndividualSMS.class);
		pu.registerEntity(GroupSMS.class);
		pu.registerEntity(Parameters.class);
		
		
	}
	
	public JedisPersistenceUnit getPersistenceUnit(){
		return pu;
	}

/*  --------------  User -----------------*/
		public void addUser(User user){	
			pu.persist(user);
		}
		public void mergeUser(User user){
			pu.merge(user);
		}
		public void removeUser(User user){	
			pu.delete(user);
		}
		public User findById(Class<User> type,long id){
			return pu.findById(User.class, id);
		}
		/********************/
		public  List<User> findAll(Class<User> type,ObjectFilter<User> filter){
			return pu.findAll(type, filter);
		}
		/********************/
		public  User findByIndexedProperty(Class<User> type,String property,String value){
			return pu.findByIndexedProperty(type,property, value);
		}
/*  --------------  IndividualSMS -----------------*/
		public void addMessage(IndividualSMS msg){
			pu.persist(msg);
		}
		public void mergeMessage(IndividualSMS msg){
			pu.merge(msg);
		}
		public void removeUser(IndividualSMS msg){
			pu.delete(msg);
		}
		public IndividualSMS findByIdMsg(Class<IndividualSMS> type, long id){
			return pu.findById(IndividualSMS.class, id);
		}
		public  List<IndividualSMS> findAllMsg(Class<IndividualSMS> type, ObjectFilter<IndividualSMS> filter){
			return pu.findAll(type, filter);
		}
		public IndividualSMS findByIndexedPropertyMsg(Class<IndividualSMS> type, String property, String value){
			return pu.findByIndexedProperty(type,property, value);
		}
		/*  --------------  GroupSMS -----------------*/
		public void addAllMessage(GroupSMS msg){
			pu.persist(msg);
		}
		public void mergeAllMessage(GroupSMS msg){
			pu.merge(msg);
		}
		public void removeAllUser(GroupSMS msg){
			pu.delete(msg);
		}
		public GroupSMS findByIdMsgAll(Class<GroupSMS> type, long id){
			return pu.findById(GroupSMS.class, id);
		}
		public  List<GroupSMS> findAllMsgAll(Class<GroupSMS> type, ObjectFilter<GroupSMS> filter){
			return pu.findAll(type, filter);
		}
		public GroupSMS findByIndexedPropertyMsgAll(Class<GroupSMS> type, String property, String value){
			return pu.findByIndexedProperty(type,property, value);
		}

		/**
	public User retrieveUser(Long id){
 
		Map<String, String> properties = jedis.hgetAll("user:" + id);
		if(!properties.containsKey("email")){
			return null;
		}
	User user = new User();
	user.setId(id);
	user.setFirstName(properties.get("firstName"));
	user.setLastName(properties.get("lastName"));
	user.setGender(properties.get("gender"));
	user.setEmail(properties.get("email"));
	user.setRole(properties.get("role"));
	user.setPassword(properties.get("password"));
	user.setPhoneNumber(properties.get("phoneNumber"));

		  return user;
		  
	}

	public void actualizeUser(User user){
	    jedis.hset("user:" + user.getId(), "firstName", user.getFirstName());
	    jedis.hset("user:" + user.getId(), "lastName", user.getLastName());
	    jedis.hset("user:" + user.getId(), "gender", user.getGender());
	    jedis.hset("user:" + user.getId(), "email",user.getEmail());
	    jedis.hset("user:" + user.getId(),"password",user.getPassword());
	    jedis.hset("user:" + user.getId(), "phoneNumber", user.getPhoneNumber());
	}

	public void removeMsgs(long id){
		jedis.del("user:"+id);
	}

	public void removeAllMsgs(long id){
		jedis.del("userAll:"+id);
	}	
	

		public void addSentMessage(String messageId, IndividualSMS sendMessage){

			sendMessage.setSmsId(messageId) ;
			Map<String, String> sms = new HashMap<String, String>();
			 
			 sms.put("smsId", sendMessage.getSmsId());
			 sms.put("phone", sendMessage.getPhone());
			 sms.put("sendDate", sendMessage.getSendDate());
			 sms.put("text", sendMessage.getText());
			 
			 jedis.hmset("sms:"+sendMessage.getIdt(), sms);
			 
		}

		
		public IndividualSMS retrieveMsgs(Long idt){
			 
			Map<String, String> properties = jedis.hgetAll("sms:" + idt);
			IndividualSMS msg = new IndividualSMS();
			msg.setIdt(idt);
			msg.setPhone(properties.get("phone"));
			msg.setSendDate(properties.get("sendDate"));
			msg.setText(properties.get("text"));
			  return msg;
			  
		}
		
		public GroupSMS retrieveAllMsgs(Long idt){
			 
			Map<String, String> properties = jedis.hgetAll("smsAll:" + idt);
		GroupSMS msgAll = new GroupSMS();
		msgAll.setIdt(idt);
		msgAll.setSendDate(properties.get("sendDate"));
		msgAll.setText(properties.get("text"));
		

			  return msgAll;
			  
		}
		
		public void addSentMessage(String messageId, GroupSMS sendAllMessage){

			sendAllMessage.setId(messageId) ;
			Map<String, String> sms = new HashMap<String, String>();
			 
			 sms.put("smsId", sendAllMessage.getId());
			// sms.put("Phone Number", sendAllMessage.getPhone());
			 sms.put("sendDate", sendAllMessage.getSendDate());
			 sms.put("text", sendAllMessage.getText());
			 
			 jedis.hmset("sms:"+sendAllMessage.getIdt(), sms);
			 
		}
		
		public void addSentMessage(String messageId,SubmitMultiResult result, GroupSMS sendAllMessage){

			sendAllMessage.setId(messageId) ;
			Map<String, String> sms = new HashMap<String, String>();
			 
			 sms.put("smsId", sendAllMessage.getId());
			 sms.put("smsALL", result.toString());
			 sms.put("sendDate", sendAllMessage.getSendDate());
			 sms.put("text", sendAllMessage.getText());
			 
			 jedis.hmset("smsAll:"+sendAllMessage.getIdt(), sms);
			 
		}
		public IndividualSMS retrieveMsg(Long id){
			 
			Map<String, String> properties = jedis.hgetAll("sms:");
			IndividualSMS msg = new IndividualSMS();
			msg.setIdt(id);
			msg.setSmsId(properties.get("smsId"));
			msg.setPhone(properties.get("phone"));
			msg.setSendDate(properties.get("sendDate"));
			msg.setText(properties.get("text"));

			  return msg;
			  
		}
		
		public GroupSMS retrieveAllMsg(Long id){
			 
			Map<String, String> properties = jedis.hgetAll("smsAll:");
			GroupSMS msgAll = new GroupSMS();
			msgAll.setIdt(id);
			msgAll.setId(properties.get("smsId"));
			msgAll.setSendDate(properties.get("sendDate"));
			msgAll.setText(properties.get("text"));
			
			  return msgAll;
			  
		}
		*/

}
