package com.smpp.platform.services;

import com.smpp.platform.entities.GroupSMS;
import com.smpp.platform.entities.IndividualSMS;
import com.smpp.platform.entities.Parameters;
import com.smpp.platform.entities.User;

import java.util.List;

public interface UserService {


    //User findById(long id);
    User findAdmin();

    //User findByEmail(String email);
    Parameters getParameters();

    //User findByPassword(String password);
    List<User> findAllUsers();
    //User findByGender(String gender);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
    //void deleteUserById(long id);

    //List<User> findAllUsers();

    User findUserById(long id);

    public boolean isUserExist(User user);

    List<IndividualSMS> findMsgs();

    List<IndividualSMS> listMessages();

    List<GroupSMS> findAllMsgs();

    List<GroupSMS> listAllMessages();

    void saveSendMessage(IndividualSMS individualSMS);

    void saveSendAllMessage(GroupSMS groupSMS);

}
