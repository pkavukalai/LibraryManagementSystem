package com.library.managementsystem.operations.authentication.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This has util methods used by authentication related operations
 * @author Pavya Kavukalai
 */
public class AuthenticationUtil {

    public static List<String> authenticationOperations = new ArrayList<>();

    static {
        authenticationOperations.add("login");
        authenticationOperations.add("register");
    }

    public static boolean isAuthenticationOperation(String operationName){
        return authenticationOperations.contains(operationName);
    }

}
