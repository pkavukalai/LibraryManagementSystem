package com.library.managementsystem.authorization.util;
import com.library.managementsystem.entities.user.User;
import com.library.managementsystem.entities.user.UserInfo;
import com.library.managementsystem.entities.user.UserRole;
import com.library.managementsystem.repositories.user.UserRepository;
import com.library.managementsystem.session.SessionManagement;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class AuthorizationUtilTest {

    @Test
    public void test_isAuthorised_roleUser_performAdminOperation(){
        SessionManagement.clearUserFromSession();
        UserInfo userInfo = new UserInfo("Bob","password2");
        User user = User.Builder.newInstance().setUserRole(UserRole.user).setUserInfo(userInfo).build();
        SessionManagement.saveUserInSession(user);
        Assert.assertFalse(AuthorizationUtil.isAuthorised("add"));
    }

    @Test
    public void test_isAuthorised_roleUser_performUserOperation(){
        SessionManagement.clearUserFromSession();
        UserInfo userInfo = new UserInfo("Bob","password2");
        User user = User.Builder.newInstance().setUserRole(UserRole.user).setUserInfo(userInfo).build();
        SessionManagement.saveUserInSession(user);
        Assert.assertTrue(AuthorizationUtil.isAuthorised("search"));
    }

    @Test
    public void test_isAuthorised_roleUser_performCommonOperation(){
        SessionManagement.clearUserFromSession();
        UserInfo userInfo = new UserInfo("Bob","password2");
        User user = User.Builder.newInstance().setUserRole(UserRole.user).setUserInfo(userInfo).build();
        SessionManagement.saveUserInSession(user);
        Assert.assertTrue(AuthorizationUtil.isAuthorised("list"));
    }

    @Test
    public void test_isAuthorised_roleAdmin_performAdminOperation(){
        SessionManagement.clearUserFromSession();
        UserInfo userInfo = new UserInfo("Alice","password1");
        User user = User.Builder.newInstance().setUserRole(UserRole.admin).setUserInfo(userInfo).build();
        SessionManagement.saveUserInSession(user);
        Assert.assertTrue(AuthorizationUtil.isAuthorised("delete"));
    }

    @Test
    public void test_isAuthorised_roleAdmin_performUserOperation(){
        SessionManagement.clearUserFromSession();
        UserInfo userInfo = new UserInfo("Alice","password1");
        User user = User.Builder.newInstance().setUserRole(UserRole.admin).setUserInfo(userInfo).build();
        SessionManagement.saveUserInSession(user);
        Assert.assertFalse(AuthorizationUtil.isAuthorised("borrow"));
    }

    @Test
    public void test_isAuthorised_roleAdmin_performCommonOperation(){
        SessionManagement.clearUserFromSession();
        UserInfo userInfo = new UserInfo("Alice","password1");
        User user = User.Builder.newInstance().setUserRole(UserRole.admin).setUserInfo(userInfo).build();
        SessionManagement.saveUserInSession(user);
        Assert.assertTrue(AuthorizationUtil.isAuthorised("list"));
    }

    @AfterClass
    public static void tearDown(){
        SessionManagement.clearUserFromSession();
        UserInfo userInfo1 = new UserInfo("Alice","password1");
        UserRepository.delete(userInfo1);
        UserInfo userInfo2 = new UserInfo("Bob","password2");
        UserRepository.delete(userInfo2);
    }

}
