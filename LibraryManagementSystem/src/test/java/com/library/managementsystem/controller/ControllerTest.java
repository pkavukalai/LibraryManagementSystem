package com.library.managementsystem.controller;

import com.library.managementsystem.entities.user.User;
import com.library.managementsystem.entities.user.UserInfo;
import com.library.managementsystem.entities.user.UserRole;
import com.library.managementsystem.session.SessionManagement;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ControllerTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

   @BeforeClass
   public static void setup(){
       UserInfo userInfo = new UserInfo("Bob","password2");
       User user = User.Builder.newInstance().setUserRole(UserRole.user).setUserInfo(userInfo).build();
       SessionManagement.saveUserInSession(user);
   }

    @Test
    public void test_performOperation_emptyArgs(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[]{};
        Controller.performOperation(args);
        Assert.assertEquals("Please specify the operation name!",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void test_performOperation_blankArg0(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[]{" "};
        Controller.performOperation(args);
        Assert.assertEquals("Please pass non null and non empty values!",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void test_performOperation_blankArgs(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[]{"add"," "};
        Controller.performOperation(args);
        Assert.assertEquals("Please pass non null and non empty values!",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void test_performOperation_nullArgs(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[2];
        args[0] = "add";
        args[1] = null;
        Controller.performOperation(args);
        Assert.assertEquals("Please pass non null and non empty values!",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void test_performOperation_invalidOperation(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[]{"test"};
        Controller.performOperation(args);
        Assert.assertEquals("Not a valid operation!",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void test_performOperation_validOperation_nonAuthentication_noUser(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[]{"search"};
        Controller.performOperation(args);
        Assert.assertEquals("Please login and perform operation!",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void test_performOperation_nonAuthentication_adminOperation_roleUser(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[]{"add"};
        Controller.performOperation(args);
        Assert.assertEquals("You are not authorised to perform this operation!",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void test_performOperation_nonAuthentication_validOperation_invalidArgs(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[]{"search"};
        Controller.performOperation(args);
        Assert.assertEquals("Please pass right set of arguments for the operation!",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void test_performOperation_validOperation_authentication_validArgs(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[]{"login","test","test"};
        Controller.performOperation(args);
        Assert.assertEquals("username or password incorrect. Please check!",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void test_performOperation_validOperation_authentication_invalidArgs(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[]{"login","test"};
        Controller.performOperation(args);
        Assert.assertEquals("Please pass right set of arguments for the operation!",
                outputStreamCaptor.toString().trim());
    }

    @AfterClass
    public static void tearDown(){
        SessionManagement.clearUserFromSession();
    }

}
