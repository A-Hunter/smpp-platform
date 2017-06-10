package com.smpp.platform.services;

import com.smpp.platform.dal.JedisPersistenceUnit;
import com.smpp.platform.dal.TrituxSMSDal;
import com.smpp.platform.entities.Parameters;
import com.smpp.platform.entities.SendAllMessage;
import com.smpp.platform.entities.SendMessage;
import com.smpp.platform.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	TrituxSMSDal db;
	private static final String KEY = "user:";

	public long counter ;

	//public  List<User> users ;
	//public  List<SendMessage> msgs ;
	//public  List<SendAllMessage> msgsAll ;
	@PostConstruct
	public void init(){
		User u = findAdmin();
		//users = populateUsers();
		//msgs = listMessages();
		//msgsAll = listAllMessages();
	}

	public Parameters getParameters(){
		Parameters p=db.getPersistenceUnit().findById(Parameters.class, 0);
		if(p==null){
			p=new Parameters();
			db.getPersistenceUnit().persist(p);
		}
		return p;
	}

	public User findAdmin() {
		Parameters p=getParameters();
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		User u=pu.findById(User.class, p.getAdminId());
		if(u==null){
			u=pu.findByIndexedProperty(User.class, "email","admin@tritux.com");
			if(u==null){
				u=new User();
				u.setFirstName("admin");
				u.setLastName("admin");
				u.setGender("male");
				u.setEmail("admin@tritux.com");
				u.setRole("admin");
				u.setPassword("admin");
				u.setPhoneNumber("123456789");
//				u.setId(0);
				pu.persist(u);
			}
			p.setAdminId(u.getId());
			pu.merge(p);
		}
		return u;
	}
	public List<User> findAllUsers() {
		return listingAllUsers();

	}

	public  List<User> listingAllUsers(){
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		List<User> ul = new ArrayList<User>();
		User u = new User();
		//return db.findUsers(null);
		ul = pu.findAll(User.class, null);
		return ul;

	}
	public void saveUser(User user) {
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		pu.persist(user);

	}
	public void updateUser(User user) {
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		pu.merge(user);
	}

	public void deleteUser(User user) {
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		pu.delete(user);
	}

//	public List<User> findByIndexProp(Property p){
//		JedisPersistenceUnit pu = db.getPersistenceUnit();
//		List<User> ul = new ArrayList<User>();
//		ul = (List<User>) pu.findByIndexedProperty(User.class, p.getPropertyType().toString(), u.getEmail());
//		return ul;
//	}

	public User findUserById(long id){
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		return pu.findById(User.class, id);
	}

	/******************************************************************************************/

	public void saveSendMessage(SendMessage sendMessage) {
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		pu.persist(sendMessage);

	}

	public void saveSendAllMessage(SendAllMessage sendAllMessage) {
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		pu.persist(sendAllMessage);

	}

	public List<SendMessage> findMsgs() {
		return listMessages();

	}

	public List<SendAllMessage> findAllMsgs() {
		return listAllMessages();

	}

	public List<SendMessage> listMessages() {
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		List<SendMessage> ul = new ArrayList<SendMessage>();
		SendMessage u = new SendMessage();
		//return db.findUsers(null);
		ul = pu.findAll(SendMessage.class, null);
		return ul;

	}

	public List<SendAllMessage> listAllMessages() {
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		List<SendAllMessage> ul = new ArrayList<SendAllMessage>();
		SendAllMessage u = new SendAllMessage();
		//return db.findUsers(null);
		ul = pu.findAll(SendAllMessage.class, null);
		return ul;

	}


	public boolean isUserExist(User user) {
		return findByEmail(user.getEmail())!=null;
	}
	public User findByEmail(String email) {
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		List<User> users = findAllUsers();
		for(User user : users){
			if(user.getEmail().equalsIgnoreCase(email)){
				User u = pu.findByIndexedProperty(User.class, "email", user.getEmail());
				return u;
			}
		}
		return null;
	}

}
