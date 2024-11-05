package com.library.managementsystem.repositories;

import com.library.managementsystem.entities.book.Book;
import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.entities.user.User;
import com.library.managementsystem.entities.user.UserInfo;
import com.library.managementsystem.entities.user.UserRole;
import com.library.managementsystem.repositories.user.UserRepository;
import org.junit.Assert;
import org.junit.Test;

public class UserRepositoryTest {


    @Test
    public void test_userRepository_CRUD(){

        String userName = "Bob";
        String password = "password2";
        UserInfo userInfo = new UserInfo(userName,password);

        //Inserting new data
        User user = User.Builder.newInstance().setUserRole
                (UserRole.user).setUserInfo
                (userInfo).build();
        Assert.assertTrue(UserRepository.insert(user));
        //Inserting same data again
        User sameUser = User.Builder.newInstance().setUserRole
                (UserRole.user).setUserInfo
                (userInfo).build();
        Assert.assertFalse(UserRepository.insert(sameUser));

        //reading data from repo
        Assert.assertTrue(UserRepository.read(userInfo)
                .getRole().equals(UserRole.user));
        //reading data which is not in repo
        UserInfo missingData = new UserInfo(userName,"password");
        Assert.assertEquals(UserRepository.read(missingData),null);

        //Updating valid data
        BookInfo bookInfo = new BookInfo("Test","testAuthor");
        Book book = Book.Builder.newInstance().setBookInfo(bookInfo).build();
        user.getBorrowedBooks().add(book);
        Assert.assertTrue(UserRepository.update(user));
        //Updating data which is not in repo
        User invalidData = User.Builder.newInstance().setUserRole
                (UserRole.user).setUserInfo
                (missingData).build();
        Assert.assertFalse(UserRepository.update(invalidData));

        //Deleting valid data
        Assert.assertTrue(UserRepository.delete(userInfo));
        Assert.assertFalse(UserRepository.delete(missingData));

    }
}
