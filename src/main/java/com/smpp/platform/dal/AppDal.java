package com.smpp.platform.dal;

import java.util.List;

import javax.annotation.PostConstruct;

import com.smpp.platform.entities.Parameters;
import com.smpp.platform.entities.SendAllMessage;
import com.smpp.platform.entities.SendMessage;
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
		pu.registerEntity(SendMessage.class);
		pu.registerEntity(SendAllMessage.class);
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
/*  --------------  SendMessage -----------------*/
		public void addMessage(SendMessage msg){	
			pu.persist(msg);
		}
		public void mergeMessage(SendMessage msg){
			pu.merge(msg);
		}
		public void removeUser(SendMessage msg){	
			pu.delete(msg);
		}
		public SendMessage findByIdMsg(Class<SendMessage> type,long id){
			return pu.findById(SendMessage.class, id);
		}
		public  List<SendMessage> findAllMsg(Class<SendMessage> type,ObjectFilter<SendMessage> filter){
			return pu.findAll(type, filter);
		}
		public  SendMessage findByIndexedPropertyMsg(Class<SendMessage> type,String property,String value){
			return pu.findByIndexedProperty(type,property, value);
		}
		/*  --------------  SendAllMessage -----------------*/
		public void addAllMessage(SendAllMessage msg){	
			pu.persist(msg);
		}
		public void mergeAllMessage(SendAllMessage msg){
			pu.merge(msg);
		}
		public void removeAllUser(SendAllMessage msg){	
			pu.delete(msg);
		}
		public SendAllMessage findByIdMsgAll(Class<SendAllMessage> type,long id){
			return pu.findById(SendAllMessage.class, id);
		}
		public  List<SendAllMessage> findAllMsgAll(Class<SendAllMessage> type,ObjectFilter<SendAllMessage> filter){
			return pu.findAll(type, filter);
		}
		public  SendAllMessage findByIndexedPropertyMsgAll(Class<SendAllMessage> type,String property,String value){
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
	

		public void addSentMessage(String messageId, SendMessage sendMessage){

			sendMessage.setSmsId(messageId) ;
			Map<String, String> sms = new HashMap<String, String>();
			 
			 sms.put("smsId", sendMessage.getSmsId());
			 sms.put("phone", sendMessage.getPhone());
			 sms.put("sendDate", sendMessage.getSendDate());
			 sms.put("text", sendMessage.getText());
			 
			 jedis.hmset("sms:"+sendMessage.getIdt(), sms);
			 
		}

		
		public SendMessage retrieveMsgs(Long idt){
			 
			Map<String, String> properties = jedis.hgetAll("sms:" + idt);
			SendMessage msg = new SendMessage();
			msg.setIdt(idt);
			msg.setPhone(properties.get("phone"));
			msg.setSendDate(properties.get("sendDate"));
			msg.setText(properties.get("text"));
			  return msg;
			  
		}
		
		public SendAllMessage retrieveAllMsgs(Long idt){
			 
			Map<String, String> properties = jedis.hgetAll("smsAll:" + idt);
		SendAllMessage msgAll = new SendAllMessage();
		msgAll.setIdt(idt);
		msgAll.setSendDate(properties.get("sendDate"));
		msgAll.setText(properties.get("text"));
		

			  return msgAll;
			  
		}
		
		public void addSentMessage(String messageId, SendAllMessage sendAllMessage){

			sendAllMessage.setId(messageId) ;
			Map<String, String> sms = new HashMap<String, String>();
			 
			 sms.put("smsId", sendAllMessage.getId());
			// sms.put("Phone Number", sendAllMessage.getPhone());
			 sms.put("sendDate", sendAllMessage.getSendDate());
			 sms.put("text", sendAllMessage.getText());
			 
			 jedis.hmset("sms:"+sendAllMessage.getIdt(), sms);
			 
		}
		
		public void addSentMessage(String messageId,SubmitMultiResult result, SendAllMessage sendAllMessage){

			sendAllMessage.setId(messageId) ;
			Map<String, String> sms = new HashMap<String, String>();
			 
			 sms.put("smsId", sendAllMessage.getId());
			 sms.put("smsALL", result.toString());
			 sms.put("sendDate", sendAllMessage.getSendDate());
			 sms.put("text", sendAllMessage.getText());
			 
			 jedis.hmset("smsAll:"+sendAllMessage.getIdt(), sms);
			 
		}
		public SendMessage retrieveMsg(Long id){
			 
			Map<String, String> properties = jedis.hgetAll("sms:");
			SendMessage msg = new SendMessage();
			msg.setIdt(id);
			msg.setSmsId(properties.get("smsId"));
			msg.setPhone(properties.get("phone"));
			msg.setSendDate(properties.get("sendDate"));
			msg.setText(properties.get("text"));

			  return msg;
			  
		}
		
		public SendAllMessage retrieveAllMsg(Long id){
			 
			Map<String, String> properties = jedis.hgetAll("smsAll:");
			SendAllMessage msgAll = new SendAllMessage();
			msgAll.setIdt(id);
			msgAll.setId(properties.get("smsId"));
			msgAll.setSendDate(properties.get("sendDate"));
			msgAll.setText(properties.get("text"));
			
			  return msgAll;
			  
		}
		*/

}
