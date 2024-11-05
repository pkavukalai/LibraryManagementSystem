package com.library.managementsystem.operations.authentication.registration.impl;

import com.library.managementsystem.entities.user.User;
import com.library.managementsystem.entities.user.UserInfo;
import com.library.managementsystem.entities.user.UserRole;
import com.library.managementsystem.operations.authentication.registration.IRegistration;
import com.library.managementsystem.repositories.user.UserRepository;
import com.library.managementsystem.session.SessionManagement;

/**
 * This does registration for the new user
 * @author Pavya Kavukalai
 */
public class Registration implements IRegistration {

    @Override
    public String getOperationName() {
        return "register";
    }

    @Override
    public boolean validateArguments(String[] arguments) {
        if (arguments.length != 4){
            return false;
        }
        try{
            UserRole.valueOf(arguments[1]);
        }catch(IllegalArgumentException e){
            return false;
        }

        return true;
    }

    @Override
    public void performOperation(String[] arguments) {
        SessionManagement.clearUserFromSession();
        boolean status = this.register(arguments[1],arguments[2],arguments[3]);
        if(status){
           System.out.println("User "+ arguments[2] +" successfully registered.");
        }else{
            System.out.println("User registered already!");
        }
    }

    @Override
    public boolean register(String userRole, String userName, String password) {
        UserInfo userInfo = new UserInfo(userName,password);
        if(null == UserRepository.read(userInfo)){
            User user = User.Builder.newInstance().setUserRole
                    (UserRole.valueOf(userRole)).setUserInfo
                    (userInfo).build();
           UserRepository.insert(user);
           return true;
        }
        return false;
    }
}
