package com.library.managementsystem.operations.admin.delete;

import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.entities.user.User;
import com.library.managementsystem.entities.user.UserInfo;
import com.library.managementsystem.entities.user.UserRole;
import com.library.managementsystem.operations.admin.add.IAddBook;
import com.library.managementsystem.operations.admin.add.impl.AddBook;
import com.library.managementsystem.operations.admin.delete.impl.DeleteBook;
import com.library.managementsystem.operations.user.borrow.IBorrowBook;
import com.library.managementsystem.operations.user.borrow.impl.BorrowBook;
import com.library.managementsystem.operations.user.returnbks.IReturnBook;
import com.library.managementsystem.operations.user.returnbks.impl.ReturnBook;
import com.library.managementsystem.session.SessionManagement;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.spy;

public class DeleteBookTest {
    IDeleteBook deleteBook = new DeleteBook();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void test_getOperationName(){
        Assert.assertEquals("delete",deleteBook.getOperationName());
    }

    @Test
    public void test_validateArgs_withValidArgs(){
        String[] args = new String[3];
        args[0] = "delete";
        args[1] = "Clean Code";
        args[2] = " Robert C. Martin";
        Assert.assertTrue(deleteBook.validateArguments(args));
    }

    @Test
    public void test_validateArgs_withInvalidArgs_incorrectLength(){
        String[] args = new String[2];
        args[0] = "add";
        args[1] = "Clean Code";
        Assert.assertFalse(deleteBook.validateArguments(args));
    }

    @Test
    public void test_performOperation_bookBorrowed_andReturned(){
        BookInfo bookInfo = new BookInfo("Clean Code","Robert C. Martin");
        System.setOut(new PrintStream(outputStreamCaptor));
        IAddBook addBook = new AddBook();
        addBook.addBook(bookInfo.getName(),bookInfo.getAuthor(),1);
        //setting user info in session
        UserInfo userInfo = new UserInfo("Bob","password2");
        User user = User.Builder.newInstance().setUserRole(UserRole.user).setUserInfo(userInfo).build();
        SessionManagement.saveUserInSession(user);
        //User borrows books
        IBorrowBook borrowBook = new BorrowBook();
        borrowBook.borrow(bookInfo);
        //Clear session
        SessionManagement.clearUserFromSession();
        String[] args = new String[3];
        args[0] = "delete";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        deleteBook.performOperation(args);
        Assert.assertEquals("Cannot delete book Clean Code because it is currently borrowed.",
                outputStreamCaptor.toString().trim());
        //User borrows books
        SessionManagement.saveUserInSession(user);
        IReturnBook returnBook = new ReturnBook();
        returnBook.returnBook(bookInfo);
        SessionManagement.clearUserFromSession();
        deleteBook.performOperation(args);
        Assert.assertTrue(outputStreamCaptor.toString().
                trim().contains("Book Clean Code is successfully deleted"));
    }

    @Test
    public void test_performOperation_bookNotBorrowed(){
        BookInfo bookInfo = new BookInfo("Clean Code","Robert C. Martin");
        System.setOut(new PrintStream(outputStreamCaptor));
        IAddBook addBook = new AddBook();
        addBook.addBook(bookInfo.getName(),bookInfo.getAuthor(),1);
        String[] args = new String[3];
        args[0] = "delete";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        deleteBook.performOperation(args);
        Assert.assertEquals("Book Clean Code is successfully deleted.",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void test_deleteBook_failed() throws NoSuchMethodException {
        BookInfo bookInfo = new BookInfo("Clean Code","Robert C. Martin");
        System.setOut(new PrintStream(outputStreamCaptor));
        IAddBook addBook = new AddBook();
        addBook.addBook(bookInfo.getName(),bookInfo.getAuthor(),1);
        DeleteBook delBook = spy(DeleteBook.class);
        String[] arguments = new String[3];
        arguments[0] = "delete";
        arguments[1] = "Clean Code";
        arguments[2] = "Robert C. Martin";
        Mockito.doNothing().when(delBook).deleteBook(Mockito.anyString(),Mockito.anyString());
        delBook.performOperation(arguments);
        Assert.assertEquals("Delete operation not successful!",
                outputStreamCaptor.toString().trim());
    }

}
