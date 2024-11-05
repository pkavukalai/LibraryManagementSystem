package com.library.managementsystem.operations.admin.add;

import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.operations.admin.add.impl.AddBook;
import com.library.managementsystem.operations.admin.delete.IDeleteBook;
import com.library.managementsystem.operations.admin.delete.impl.DeleteBook;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.spy;

public class AddBookTest {

    IAddBook addBook = new AddBook();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void test_getOperationName(){
        Assert.assertEquals("add",addBook.getOperationName());
    }

    @Test
    public void test_validateArgs_withValidArgs(){
        String[] args = new String[4];
        args[0] = "add";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        args[3] = "3";
        Assert.assertTrue(addBook.validateArguments(args));
    }

    @Test
    public void test_validateArgs_withInvalidArgs_incorrectLength(){
        String[] args = new String[3];
        args[0] = "add";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        Assert.assertFalse(addBook.validateArguments(args));
    }

    @Test
    public void test_validateArgs_withInvalidArgs_invalidData(){
        String[] args = new String[4];
        args[0] = "add";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        args[3] = "test";
        Assert.assertFalse(addBook.validateArguments(args));
    }


    @Test
    public void test_performOperation_newBookType(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[4];
        args[0] = "add";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        args[3] = "1";
        addBook.performOperation(args);
        Assert.assertEquals("Book Clean Code by Robert C. Martin " +
                        "added successfully, inventory: 1.",
                outputStreamCaptor.toString().trim());
        BookInfo bookInfo = new BookInfo("Clean Code","Robert C. Martin");
        //Teardown
        IDeleteBook deleteBook = new DeleteBook();
        deleteBook.deleteBook(bookInfo.getName(), bookInfo.getAuthor());
    }

    @Test
    public void test_performOperation_bookTypeAlreadyPresent(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[4];
        args[0] = "add";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        args[3] = "1";
        addBook.performOperation(args);
        Assert.assertEquals("Book Clean Code by Robert C. Martin " +
                        "added successfully, inventory: 1.",
                outputStreamCaptor.toString().trim());
        addBook.performOperation(args);
        Assert.assertTrue(outputStreamCaptor.toString().trim().contains("Book Clean Code inventory " +
                "successfully updated, new inventory: 2."));
        BookInfo bookInfo = new BookInfo("Clean Code","Robert C. Martin");
        //Teardown
        IDeleteBook deleteBook = new DeleteBook();
        deleteBook.deleteBook(bookInfo.getName(), bookInfo.getAuthor());
    }

    @Test
    public void test_addBook_oneBook(){
        BookInfo bookInfo = new BookInfo("Clean Code","Robert C. Martin");
        Integer result = addBook.addBook("Clean Code","Robert C. Martin",1);
        Assert.assertTrue(result >= 1);
        //Teardown
        IDeleteBook deleteBook = new DeleteBook();
        deleteBook.deleteBook(bookInfo.getName(), bookInfo.getAuthor());
    }

    @Test
    public void test_addBook_multipleBooks(){
        BookInfo bookInfo = new BookInfo("Clean Code","Robert C. Martin");
        Integer result = addBook.addBook("Clean Code","Robert C. Martin",3);
        Assert.assertTrue(result >= 3);
        //Teardown
        IDeleteBook deleteBook = new DeleteBook();
        deleteBook.deleteBook(bookInfo.getName(), bookInfo.getAuthor());
    }

    @Test
    public void test_addBook_failed(){
        System.setOut(new PrintStream(outputStreamCaptor));
        AddBook addBk = spy(AddBook.class);
        String[] arguments = new String[4];
        arguments[0] = "add";
        arguments[1] = "Clean Code";
        arguments[2] = "Robert C. Martin";
        arguments[3] = "5";
        Mockito.when(addBk.addBook
                        (Mockito.anyString(),Mockito.anyString(),Mockito.anyInt())).
                thenReturn(0);
        addBk.performOperation(arguments);
        Assert.assertEquals("Add book operation not successful!",
                outputStreamCaptor.toString().trim());
    }

}
