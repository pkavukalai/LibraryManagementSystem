package com.library.managementsystem.controller;

import com.library.managementsystem.authorization.util.AuthorizationUtil;
import com.library.managementsystem.operations.IOperation;
import com.library.managementsystem.operations.OperationsFactory;
import com.library.managementsystem.operations.authentication.util.AuthenticationUtil;
import com.library.managementsystem.session.SessionManagement;

/**
 * This class validates inputs and performs Operations for the incoming commands
 * @author Pavya Kavukalai
 */
public class Controller {
    /**
     * Validate Inputs, get Operations object and perform operations
     * @param args
     */
    public static void performOperation(String[] args) {
        if (validateInputs(args)) {
            OperationsFactory.getInstance().getOperationObject(args[0]).performOperation(args);
        }
    }
    /**
     * This method validates the input arguments
     * @param args
     * @return
     */
    private static boolean validateInputs(String[] args) {
        if (args.length < 1) {
            System.out.println("Please specify the operation name!");
            return false;
        }
        for (int i = 0; i < args.length; i++) {
            if (null == args[i] || args[i].trim().equals("")) {
                System.out.println("Please pass non null and non empty values!");
                return false;
            }
        }
        String operationName = args[0];
        IOperation operation = OperationsFactory.getInstance().getOperationObject(operationName);
        if (null == operation) {
            System.out.println("Not a valid operation!");
            return false;
        }
        boolean isAuthentication = AuthenticationUtil.isAuthenticationOperation(operationName);
        if (!isAuthentication && null == SessionManagement.getUserFromSession()) {
            System.out.println("Please login and perform operation!");
            return false;
        }
        if (!isAuthentication &&
                !AuthorizationUtil.isAuthorised(operationName)) {
            System.out.println("You are not authorised to perform this operation!");
            return false;
        }
        if (!operation.validateArguments(args)) {
            System.out.println("Please pass right set of arguments for the operation!");
            return false;
        }
        return true;
    }
}
