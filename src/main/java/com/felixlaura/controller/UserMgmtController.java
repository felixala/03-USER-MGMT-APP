package com.felixlaura.controller;

import com.felixlaura.bindings.ActiveAccount;
import com.felixlaura.bindings.Login;
import com.felixlaura.bindings.User;
import com.felixlaura.service.UserMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserMgmtController {

    @Autowired
    private UserMgmtService service;

    @PostMapping("/user")
    public ResponseEntity<String> userRegistration(@RequestBody User user){
        boolean saveUser = service.saveUser(user);
        if(saveUser){
            return new ResponseEntity<>("Registration Success", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Registration Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestBody ActiveAccount activeAccount){
        boolean isActivated = service.activateUserAcc(activeAccount);
        if (isActivated){
            return new ResponseEntity<>("Account Activated", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid Temporary Password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = service.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId){
        User user = service.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer userId){
        boolean isDeleted = service.deleteUserById(userId);
        if(isDeleted){
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}/{status}")
    public ResponseEntity<String> statusChange(@PathVariable Integer userId, @PathVariable String status){
        boolean isChanged = service.changeAccStatus(userId, status);
        if(isChanged){
            return new ResponseEntity<>("Status Changed", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Failed Change", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login login){
        String status = service.login(login);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/forgotPwd/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email){
        String status = service.forgotPassword(email);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }


}
