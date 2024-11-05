package com.library.managementsystem.authorization.util;

import com.library.managementsystem.entities.user.UserRole;
import com.library.managementsystem.session.SessionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class checks whether the user is authorised to perform the specified operation
 * @author Pavya Kavukalai
 */
public class AuthorizationUtil {

    private static final List<String> adminOperations = new ArrayList<>();
    private static final List<String> userOperations = new ArrayList<>();

    static{
        //initializing admin operations
        adminOperations.add("add");
        adminOperations.add("delete");
        //initializing user operations
        userOperations.add("search");
        userOperations.add("borrow");
        userOperations.add("return");
    }

    public static boolean isAuthorised(String operation) {
        if(adminOperations.contains(operation)){
            return SessionManagement.getUserFromSession().getRole().equals(UserRole.admin);
        }
        if(userOperations.contains(operation)){
            return SessionManagement.getUserFromSession().getRole().equals(UserRole.user);
        }
        return true;
    }
}
