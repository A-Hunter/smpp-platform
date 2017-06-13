package com.smpp.platform.services;

import com.smpp.platform.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthenticationService {

    public List<User> users;

    @Autowired
    public UserServiceImpl cs;

    /**
     * @PostConstruct public void init(){ users = cs.populateUsers(); }
     */
    public User authenticate(String email, String password) {
        User u = null;
        users = cs.findAllUsers();
        for (User user : users) {
            if (user.getEmail().equals(email)
                    && user.getPassword().equals(password)) {
                System.out.println("This user exists already ");
                u = user;// cs.findByEmail(email);
                break;
            }
        }
        return u;
    }
}
