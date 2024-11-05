package com.library.managementsystem.session;

import com.library.managementsystem.entities.user.User;

public class SessionManagement {

    private static User loggedInUser;

    public static void saveUserInSession(User user){
        loggedInUser = user;
    }

    public static User getUserFromSession(){
        return loggedInUser;
    }

   public static void clearUserFromSession(){
       loggedInUser = null;
   }


}
