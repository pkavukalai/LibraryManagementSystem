package com.library;

import com.library.managementsystem.controller.Controller;
import com.library.managementsystem.entities.user.UserRole;
import com.library.managementsystem.session.SessionManagement;

import java.util.Scanner;

/**
 * Run this class to start the application.
 * Do system exit to stop the application. By pressing the stop button.
 * @author Pavya Kavukalai
 */
public class Main {

    public static void main(String[] args) {
        do {
            welcomeMessageScreen();
            Authenticate();
            DisplayOperationMenu();
            performChosenOperation();
            ExitMessageScreen();
        } while (true);
    }

    private static void welcomeMessageScreen() {
        String welcomeMessage = "Welcome to Online Library management system!" + "\n"
                + "Please do login if you have an existing account or register if you don't have an account." + "\n"
                + "1. To Register: type register role[admin/user] username password." +
                " sample - register admin Alice password1" + "\n"
                + "2. To Login: type login username password. sample - login Bob password2";
        System.out.println(welcomeMessage);
    }

    private static void ScanUserInputsAndPerformOperation() {
        String[] inputs = null;
        Scanner sc = new Scanner(System.in).useDelimiter("\\n");
        String arg = sc.next();
        if(arg.contains("\"")){
            arg = arg.replace( " \"","-");
            arg = arg.replace( "\" ","-");
            arg = arg.replace( "\"","");
            inputs = arg.split("-");
        }else{
            inputs = arg.split(" ");
        }
        Controller.performOperation(inputs);
    }

    private static void Authenticate() {
        ScanUserInputsAndPerformOperation();
        if (null == SessionManagement.getUserFromSession()) {
            displayRegisterLoginMessage();
            Authenticate();
        }
    }

    private static void displayRegisterLoginMessage() {
        String LoginMessage = "register or login";
        System.out.println(LoginMessage);
    }

    private static void DisplayOperationMenu() {
        if (SessionManagement.getUserFromSession().getRole().equals(UserRole.user)) {
            displayUserCommands();
        } else if (SessionManagement.getUserFromSession().getRole().equals(UserRole.admin)) {
            displayAdminCommands();
        }
    }

    private static void performChosenOperation() {
        String userResponse = "N";
        do {
            System.out.println("type your command!");
            ScanUserInputsAndPerformOperation();
            System.out.println("Do you want to continue? Type Y to continue");
            Scanner yesOrNoScanner = new Scanner(System.in);
            userResponse = yesOrNoScanner.next();

        } while (userResponse.equals("Y"));
    }

    private static void displayAdminCommands() {
        System.out.println("You can perform any of the below operations ");
        System.out.println("1. type list - sample : list");
        System.out.println("2. type add bookName authorName bookCount - sample : add \"Harry Potter\" \"JK Rowling\" 5");
        System.out.println("3. type delete bookName authorName - sample : delete \"Harry Potter\" \"JK Rowling\"");
    }

    private static void displayUserCommands() {
        System.out.println("You can perform any of the below operations ");
        System.out.println("1. list - sample : list");
        System.out.println("2. search - sample : search \"Harry Potter\" \"JK Rowling\"");
        System.out.println("3. borrow - sample : borrow \"Harry Potter\" \"JK Rowling\"");
        System.out.println("4. return - sample : return \"Harry Potter\" \"JK Rowling\"");
    }

    private static void ExitMessageScreen() {
        SessionManagement.clearUserFromSession();
        System.out.println("Thank you !");
        System.out.println("");
        System.out.println("");
    }

}