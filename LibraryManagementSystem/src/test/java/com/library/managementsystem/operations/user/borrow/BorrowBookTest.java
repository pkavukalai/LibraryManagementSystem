package com.library.managementsystem.operations.user.borrow;

import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.entities.user.User;
import com.library.managementsystem.entities.user.UserInfo;
import com.library.managementsystem.entities.user.UserRole;
import com.library.managementsystem.operations.admin.add.IAddBook;
import com.library.managementsystem.operations.admin.add.impl.AddBook;
import com.library.managementsystem.operations.admin.delete.IDeleteBook;
import com.library.managementsystem.operations.admin.delete.impl.DeleteBook;
import com.library.managementsystem.operations.user.borrow.impl.BorrowBook;
import com.library.managementsystem.operations.user.returnbks.IReturnBook;
import com.library.managementsystem.operations.user.returnbks.impl.ReturnBook;
import com.library.managementsystem.session.SessionManagement;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BorrowBookTest {

  IBorrowBook borrowBook = new BorrowBook();
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void test_getOperationName(){
        Assert.assertEquals("borrow",borrowBook.getOperationName());
    }

    @Test
    public void test_validateArgs_withValidArgs(){
        String[] args = new String[3];
        args[0] = "borrow";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        Assert.assertTrue(borrowBook.validateArguments(args));
    }

    @Test
    public void test_validateArgs_withInvalidArgs_incorrectLength(){
        String[] args = new String[2];
        args[0] = "borrow";
        args[1] = "Clean Code";
        Assert.assertFalse(borrowBook.validateArguments(args));
    }

    @Test
    public void test_performOperation_bookBorrowed_success(){
        BookInfo bookInfo = new BookInfo("Clean Code","Robert C. Martin");
        System.setOut(new PrintStream(outputStreamCaptor));
        IAddBook addBook = new AddBook();
        addBook.addBook(bookInfo.getName(),bookInfo.getAuthor(),1);
        //setting user info in session
        UserInfo userInfo = new UserInfo("Bob","password2");
        User user = User.Builder.newInstance().setUserRole(UserRole.user).setUserInfo(userInfo).build();
        SessionManagement.saveUserInSession(user);
        //User borrows books
        String[] args = new String[3];
        args[0] = "borrow";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        borrowBook.performOperation(args);
        Assert.assertEquals("Book "+args[1]+" successfully borrowed.",
                outputStreamCaptor.toString().trim());
        //Clear session
        SessionManagement.clearUserFromSession();
        //User return books
        SessionManagement.saveUserInSession(user);
        IReturnBook returnBook = new ReturnBook();
        returnBook.returnBook(bookInfo);
        SessionManagement.clearUserFromSession();
        //Delete the added Book
        IDeleteBook deleteBook = new DeleteBook();
        deleteBook.performOperation(args);
    }

    @Test
    public void test_performOperation_bookBorrowed_failure(){
        System.setOut(new PrintStream(outputStreamCaptor));
        //setting user info in session
        UserInfo userInfo = new UserInfo("Bob","password2");
        User user = User.Builder.newInstance().setUserRole(UserRole.user).setUserInfo(userInfo).build();
        SessionManagement.saveUserInSession(user);
        //User borrows books
        String[] args = new String[3];
        args[0] = "borrow";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        borrowBook.performOperation(args);
        Assert.assertEquals("Book not available to borrow!",
                outputStreamCaptor.toString().trim());
        //Clear session
        SessionManagement.clearUserFromSession();
    }

    @Test
    public void test_performOperation_2Borrows_oneSuccess_oneFailure(){
        BookInfo bookInfo = new BookInfo("Clean Code","Robert C. Martin");
        System.setOut(new PrintStream(outputStreamCaptor));
        IAddBook addBook = new AddBook();
        addBook.addBook(bookInfo.getName(),bookInfo.getAuthor(),1);
        //setting user info in session
        UserInfo userInfo = new UserInfo("Bob","password2");
        User user = User.Builder.newInstance().setUserRole(UserRole.user).setUserInfo(userInfo).build();
        SessionManagement.saveUserInSession(user);
        //User borrows books
        String[] args = new String[3];
        args[0] = "borrow";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        borrowBook.performOperation(args);
        Assert.assertEquals("Book "+args[1]+" successfully borrowed.",
                outputStreamCaptor.toString().trim());
        borrowBook.performOperation(args);
        Assert.assertTrue(outputStreamCaptor.toString().trim().
                contains("Book not available to borrow!"));
        //Clear session
        SessionManagement.clearUserFromSession();
        //User return books
        SessionManagement.saveUserInSession(user);
        IReturnBook returnBook = new ReturnBook();
        returnBook.returnBook(bookInfo);
        SessionManagement.clearUserFromSession();
        //Delete the added Book
        IDeleteBook deleteBook = new DeleteBook();
        deleteBook.performOperation(args);
    }


}
