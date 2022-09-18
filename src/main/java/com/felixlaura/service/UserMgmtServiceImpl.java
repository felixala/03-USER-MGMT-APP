package com.felixlaura.service;

import com.felixlaura.bindings.ActiveAccount;
import com.felixlaura.bindings.Login;
import com.felixlaura.bindings.User;
import com.felixlaura.entity.UserMaster;
import com.felixlaura.repository.UserMgmtRepository;
import com.felixlaura.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.List;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

    @Autowired
    private UserMgmtRepository userRepo;

    @Autowired
    private EmailUtils emailUtils;

    @Override
    public boolean saveUser(User user) {
        UserMaster entity = new UserMaster();
        BeanUtils.copyProperties(user, entity);
        entity.setAccStatus("IN-ACTIVE");
        entity.setPassword(generateRandomPassword());
        UserMaster save = userRepo.save(entity);

        //TODO: Send email to activate account
        String subject = "Your Registration Success";
        String fileName = "REG-EMAIL-BODY.txt";
        String body = readRegEmailBody(entity.getFullName(), entity.getPassword(), fileName);

        emailUtils.sendEmail(user.getEmail(), subject, body);

        return save.getUserId() != null;
    }

    @Override
    public boolean activateUserAcc(ActiveAccount activeAccount) {

        UserMaster entity = new UserMaster();
        entity.setEmail(activeAccount.getEmail());
        entity.setPassword(activeAccount.getTempPwd());

        Example<UserMaster> of = Example.of(entity);
        List<UserMaster> findAll = userRepo.findAll(of);

        if(findAll.isEmpty()){
            return false; // email and password are not in the database
        }else {
            UserMaster userMaster = findAll.get(0);
            /*
            It is a good practice to validate "new password" and "confirm password" in the frontend
            Since a new a password has note been entered in the database
             */
            //if(activeAccount.getNewPwd().equals(activeAccount.getConfirmPwd())){
                userMaster.setPassword(activeAccount.getNewPwd());
                userMaster.setAccStatus("ACTIVE");
                userRepo.save(userMaster);
                return true;
            //}

        }
        //return false;
    }

    @Override
    public boolean deleteUserById(Integer userId) {
        try{
           userRepo.deleteById(userId);
           return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        List<UserMaster> findAll = userRepo.findAll();
        List<User> users = new ArrayList<>();

        for(UserMaster entity : findAll){
            User user = new User();
            BeanUtils.copyProperties(entity, user);
            users.add(user);
        }
        return users;
    }

    @Override
    public User getUserById(Integer userId) {

        Optional<UserMaster> findById = userRepo.findById(userId);

        if(findById.isPresent()){
            User user = new User();
            UserMaster userMaster = findById.get();
            BeanUtils.copyProperties(userMaster, user);

            return user;
        }
        return null;
    }

    @Override
    public boolean changeAccStatus(Integer userId, String status) {
        Optional<UserMaster> findById = userRepo.findById(userId);
        if(findById.isPresent()){
            UserMaster userMaster = findById.get();
            userMaster.setAccStatus(status);
            userRepo.save(userMaster);
            return true;
        }

        return false;
    }

    @Override
    public String login(Login login) {
        UserMaster entity = new UserMaster();
        entity.setEmail(login.getEmail());
        entity.setPassword(login.getPassword());

        Example<UserMaster> of = Example.of(entity);
        List<UserMaster> findAll = userRepo.findAll(of);

        if(findAll.isEmpty()){
            return "Invalid Credentials";
        }else {
            UserMaster userMaster = findAll.get(0);
            if(userMaster.getAccStatus().equals("ACTIVE")){
                return "SUCCESS";
            }else {
                return "Account not activated";
            }
        }
    }

    @Override
    public String forgotPassword(String email) {
        UserMaster entity = userRepo.findByEmail(email);
        if(entity == null){
            return "Invalid Credentials";
        }
        //TODO: Email Service
        String subject = "Forgot Password";
        String fileName ="RECOVER-MAIL-BODY.txt";
        String body = readRegEmailBody(entity.getFullName(), entity.getPassword(), fileName);

        boolean sendEmail = emailUtils.sendEmail(entity.getEmail(), subject, body);

        if(sendEmail){
            return "Password sent to you registered email";
        }
        return null;
    }

    private String generateRandomPassword() {

        // create a string of uppercase and lowercase characters and numbers
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        // combine all strings
        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 10;

        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphaNumeric.length());

            // get character specified by index
            // from the string
            char randomChar = alphaNumeric.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

       return sb.toString();
    }

    private String readRegEmailBody(String fullName, String pwd, String fileName){
        String url = "";
        String mailBody = null;
        try{
            FileReader file = new FileReader(fileName);
            BufferedReader br = new BufferedReader(file);
            StringBuffer buffer = new StringBuffer();
            String line = br.readLine();

            while (line != null){
                buffer.append(line);
                line = br.readLine(); // Read next line
            }
            br.close();
            mailBody = buffer.toString();
            mailBody = mailBody.replace("{FULL_NAME}", fullName);
            mailBody = mailBody.replace("{TEMP_PWD}", pwd);
            mailBody = mailBody.replace("{URL}", url);
            mailBody = mailBody.replace("{PWD}", pwd);

        }catch (Exception e){
            e.printStackTrace();
        }
        return mailBody;

    }

}
