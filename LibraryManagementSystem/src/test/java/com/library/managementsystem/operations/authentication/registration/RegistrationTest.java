package com.library.managementsystem.operations.authentication.registration;

import com.library.managementsystem.entities.user.UserInfo;
import com.library.managementsystem.operations.authentication.registration.IRegistration;
import com.library.managementsystem.operations.authentication.registration.impl.Registration;
import com.library.managementsystem.repositories.user.UserRepository;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class RegistrationTest {

    IRegistration registration = new Registration();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void test_getOperationName(){
        Assert.assertEquals("register",registration.getOperationName());
    }

    @Test
    public void test_validateArgs_withValidArgs(){
        String[] args = new String[4];
        args[0] = "register";
        args[1] = "user";
        args[2] = "Bob";
        args[3] = "password2";
        Assert.assertTrue(registration.validateArguments(args));
    }

    @Test
    public void test_validateArgs_withInvalidArgs_incorrectLength(){
        String[] args = new String[2];
        args[0] = "register";
        args[1] = "user";
        Assert.assertFalse(registration.validateArguments(args));
    }

    @Test
    public void test_validateArgs_withInvalidArgs_incorrectUserRole(){
        String[] args = new String[4];
        args[0] = "register";
        args[1] = "test";
        args[2] = "Bob";
        args[3] = "password2";
        Assert.assertFalse(registration.validateArguments(args));
    }

    @Test
    public void test_performOperation_registration_successful(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[4];
        args[0] = "register";
        args[1] = "user";
        args[2] = "Bob";
        args[3] = "password2";
        registration.performOperation(args);
        Assert.assertEquals("User Bob successfully registered.",
                outputStreamCaptor.toString().trim());
        //teardown
        UserInfo userInfo = new UserInfo(args[2],args[3]);
        UserRepository.delete(userInfo);
    }

    @Test
    public void test_performOperation_attempting_registration_again(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[4];
        args[0] = "register";
        args[1] = "user";
        args[2] = "Bob";
        args[3] = "password2";
        registration.performOperation(args);
        registration.performOperation(args);
        Assert.assertTrue(
                outputStreamCaptor.toString().trim().contains("User registered already!"));
        //teardown
        UserInfo userInfo = new UserInfo(args[2],args[3]);
        UserRepository.delete(userInfo);
    }

}
