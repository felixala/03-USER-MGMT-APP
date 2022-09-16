package com.felixlaura.service;


import com.felixlaura.bindings.ActiveAccount;
import com.felixlaura.bindings.Login;
import com.felixlaura.bindings.User;

import java.util.List;

public interface UserMgmtService {

    public boolean saveUser(User user);

    public boolean activateUserAcc(ActiveAccount activeAccount);

    public boolean deleteUserById(Integer userId);

    public List<User> getAllUsers();

    public User findUserById(Integer userId);

    public boolean changeAccStatus(Integer userId, String status);

    public String login(Login login);

    public String forgotPassword(String email);

}
