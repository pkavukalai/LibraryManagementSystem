package com.library.managementsystem.session;

import com.library.managementsystem.entities.user.User;
import com.library.managementsystem.entities.user.UserInfo;
import com.library.managementsystem.entities.user.UserRole;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SessionManagementTest {

    UserInfo userInfo = null;
    User user = null;

    @Before
    public void test_setup(){
        userInfo = new UserInfo("Bob","password2");
        user = User.Builder.newInstance().setUserRole
                (UserRole.user).setUserInfo(userInfo).build();
    }

    @Test
    public void test_saveUserInSession(){
        Assert.assertNull(SessionManagement.getUserFromSession());
        SessionManagement.saveUserInSession(user);
        Assert.assertNotNull(SessionManagement.getUserFromSession());
    }

    @Test
    public void test_clearUserFromSession(){
        SessionManagement.saveUserInSession(user);
        Assert.assertNotNull(SessionManagement.getUserFromSession());
        SessionManagement.clearUserFromSession();
        Assert.assertNull(SessionManagement.getUserFromSession());
    }

    @After
    public void tearDown(){
        SessionManagement.clearUserFromSession();
    }

}
