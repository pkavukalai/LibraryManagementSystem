package com.library.managementsystem.operations.authentication.login.impl;

import com.library.managementsystem.entities.user.User;
import com.library.managementsystem.entities.user.UserInfo;
import com.library.managementsystem.operations.authentication.login.ILogin;
import com.library.managementsystem.repositories.user.UserRepository;
import com.library.managementsystem.session.SessionManagement;

/**
 * This class has login operations
 * @author Pavya Kavukalai
 */
public class Login implements ILogin {

    @Override
    public String getOperationName() {
        return "login";
    }

    @Override
    public boolean validateArguments(String[] arguments) {
        return arguments.length == 3;
    }

    @Override
    public void performOperation(String[] arguments) {
        boolean status = this.login(arguments[1],arguments[2]);
        if(status){
            System.out.println("User " + arguments[1] + " successfully logged in.");
        }else{
            System.out.println("username or password incorrect. Please check!");
        }
    }

    @Override
    public boolean login(String userName,String password) {
        SessionManagement.clearUserFromSession();
        User user = UserRepository.read(new UserInfo(userName,password));
        if(null != user){
            SessionManagement.saveUserInSession(user);
            return true;
        }
        return false;
    }
}
