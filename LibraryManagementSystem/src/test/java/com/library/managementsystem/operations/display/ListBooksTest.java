package com.library.managementsystem.operations.display;

import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.operations.admin.add.IAddBook;
import com.library.managementsystem.operations.admin.add.impl.AddBook;
import com.library.managementsystem.operations.admin.delete.IDeleteBook;
import com.library.managementsystem.operations.admin.delete.impl.DeleteBook;
import com.library.managementsystem.operations.display.impl.ListBooks;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ListBooksTest {

    IListBooks listBooks = new ListBooks();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void test_getOperationName(){
        Assert.assertEquals("list",listBooks.getOperationName());
    }

    @Test
    public void test_validateArgs_withValidArgs(){
        String[] args = new String[1];
        args[0] = "list";
        Assert.assertTrue(listBooks.validateArguments(args));
    }

    @Test
    public void test_validateArgs_withInvalidArgs_incorrectLength(){
        String[] args = new String[2];
        args[0] = "list";
        Assert.assertFalse(listBooks.validateArguments(args));
    }

    @Test
    public void test_performOperation_listBooks_success(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[1];
        args[0] = "list";
        BookInfo bookInfo = new BookInfo("Clean Code","Robert C. Martin");
        IAddBook addBook = new AddBook();
        addBook.addBook("Clean Code","Robert C. Martin",5);
        listBooks.performOperation(args);
        Assert.assertEquals("1. Clean Code - Robert C. Martin - Inventory: 5.",
                outputStreamCaptor.toString().trim());
        //teardown
        IDeleteBook deleteBook = new DeleteBook();
        deleteBook.deleteBook(bookInfo.getName(),bookInfo.getAuthor());
    }

    @Test
    public void test_performOperation_listBooks_fail(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[1];
        args[0] = "list";
        listBooks.performOperation(args);
        Assert.assertEquals("No books available!",
                outputStreamCaptor.toString().trim());
    }
}
