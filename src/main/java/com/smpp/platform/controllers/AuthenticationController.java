package com.smpp.platform.controllers;

import com.smpp.platform.entities.Login;
import com.smpp.platform.entities.User;
import com.smpp.platform.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/api")
@ComponentScan("com.smpp.platform")
public class AuthenticationController {

	@Autowired
    AuthenticationService autoServ;
   
    
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity authenticateTheUser(@RequestBody final Login login )throws Exception {
        System.out.println("Searching for User with the email : " + login.getEmail());
        User u=autoServ.authenticate(login.getEmail(), login.getPassword());
        if(u==null){
        	return ResponseEntity.status(HttpStatus.OK).body(null);
        }else{
        	return ResponseEntity.status(HttpStatus.OK).body(u);
        }
   
    }
 
}