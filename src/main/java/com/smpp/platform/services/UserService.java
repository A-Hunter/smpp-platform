package com.smpp.platform.services;

import com.smpp.platform.entities.Parameters;
import com.smpp.platform.entities.SendAllMessage;
import com.smpp.platform.entities.SendMessage;
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

    List<SendMessage> findMsgs();

    List<SendMessage> listMessages();

    List<SendAllMessage> findAllMsgs();

    List<SendAllMessage> listAllMessages();

    void saveSendMessage(SendMessage sendMessage);

    void saveSendAllMessage(SendAllMessage sendAllMessage);

    public boolean isUserExist(User user);

}
