package com.library.managementsystem.operations.authentication.login;

import com.library.managementsystem.entities.user.UserInfo;
import com.library.managementsystem.operations.authentication.login.ILogin;
import com.library.managementsystem.operations.authentication.login.impl.Login;
import com.library.managementsystem.operations.authentication.registration.IRegistration;
import com.library.managementsystem.operations.authentication.registration.impl.Registration;
import com.library.managementsystem.repositories.user.UserRepository;
import com.library.managementsystem.session.SessionManagement;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LoginTest {
    ILogin login = new Login();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void test_getOperationName(){
        Assert.assertEquals("login",login.getOperationName());
    }

    @Test
    public void test_validateArgs_withValidArgs(){
        String[] args = new String[3];
        args[0] = "login";
        args[1] = "Bob";
        args[2] = "password2";
        Assert.assertTrue(login.validateArguments(args));
    }

    @Test
    public void test_validateArgs_withInvalidArgs_incorrectLength(){
        String[] args = new String[2];
        args[0] = "login";
        args[1] = "Bob";
        Assert.assertFalse(login.validateArguments(args));
    }

    @Test
    public void test_performOperation_login_successful(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[3];
        args[0] = "login";
        args[1] = "Bob";
        args[2] = "password2";
        //setup
        IRegistration registration = new Registration();
        registration.register("user", args[1],args[2]);
        login.performOperation(args);
        Assert.assertEquals("User Bob successfully logged in.",
                outputStreamCaptor.toString().trim());
        //teardown
        UserInfo userInfo = new UserInfo(args[1],args[2]);
        UserRepository.delete(userInfo);
        SessionManagement.clearUserFromSession();
    }

    @Test
    public void test_performOperation_login_failure_noValidDataFound(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[3];
        args[0] = "login";
        args[1] = "Bob";
        args[2] = "password2";
        //setup
        login.performOperation(args);
        Assert.assertEquals("username or password incorrect. Please check!",
                outputStreamCaptor.toString().trim());

    }

}
