package com.library.managementsystem.repositories.user;

import com.library.managementsystem.entities.user.User;
import com.library.managementsystem.entities.user.UserInfo;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static Map<UserInfo, User> users = new HashMap<>();

    public static User read(UserInfo userInfo){
        if(users.containsKey(userInfo)){
           return users.get(userInfo);
        }
        return null;
    }

    public static boolean insert(User user){
        if(users.containsKey(user.getUserInfo())){
            return false;
        }
        users.put(user.getUserInfo(), user);
        return true;
    }

    public static boolean update(User user){
        if(!users.containsKey(user.getUserInfo())){
            return false;
        }
        users.put(user.getUserInfo(),user);
        return true;
    }

    public static boolean delete(UserInfo userInfo){
        if(!users.containsKey(userInfo)){
            return false;
        }
        users.remove(userInfo);
        return true;
    }

}
