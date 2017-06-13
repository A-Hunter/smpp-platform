package com.smpp.platform.services;

import com.smpp.platform.dal.JedisPersistenceUnit;
import com.smpp.platform.dal.AppDal;
import com.smpp.platform.entities.GroupSMS;
import com.smpp.platform.entities.IndividualSMS;
import com.smpp.platform.entities.Parameters;
import com.smpp.platform.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
    AppDal db;
	private static final String KEY = "user:";

	@PostConstruct
	public void init(){
		User u = findAdmin();
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

	public User findUserById(long id){
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		return pu.findById(User.class, id);
	}

	public void saveSendMessage(IndividualSMS individualSMS) {
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		pu.persist(individualSMS);

	}

	public void saveSendAllMessage(GroupSMS groupSMS) {
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		pu.persist(groupSMS);

	}

	public List<IndividualSMS> findMsgs() {
		return listMessages();

	}

	public List<GroupSMS> findAllMsgs() {
		return listAllMessages();

	}

	public List<IndividualSMS> listMessages() {
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		List<IndividualSMS> ul = new ArrayList<IndividualSMS>();
		IndividualSMS u = new IndividualSMS();
		ul = pu.findAll(IndividualSMS.class, null);
		return ul;

	}

	public List<GroupSMS> listAllMessages() {
		JedisPersistenceUnit pu = db.getPersistenceUnit();
		List<GroupSMS> ul = new ArrayList<GroupSMS>();
		GroupSMS u = new GroupSMS();
		ul = pu.findAll(GroupSMS.class, null);
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
