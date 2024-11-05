package com.library.managementsystem.operations.user.search;

import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.operations.admin.add.IAddBook;
import com.library.managementsystem.operations.admin.add.impl.AddBook;
import com.library.managementsystem.operations.admin.delete.IDeleteBook;
import com.library.managementsystem.operations.admin.delete.impl.DeleteBook;
import com.library.managementsystem.operations.user.search.impl.SearchBook;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SearchBooksTest {

    ISearchBook searchBook = new SearchBook();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    public void test_getOperationName(){
        Assert.assertEquals("search",searchBook.getOperationName());
    }

    @Test
    public void test_validateArgs_withValidArgs(){
        String[] args = new String[3];
        args[0] = "search";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        Assert.assertTrue(searchBook.validateArguments(args));
    }

    @Test
    public void test_validateArgs_withInvalidArgs_incorrectLength(){
        String[] args = new String[2];
        args[0] = "search";
        args[1] = "Clean Code";
        Assert.assertFalse(searchBook.validateArguments(args));
    }

    @Test
    public void test_performOperation_noBooksAvailable(){
        System.setOut(new PrintStream(outputStreamCaptor));
        String[] args = new String[3];
        args[0] = "search";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        searchBook.performOperation(args);
        Assert.assertEquals("Clean Code - Robert C. Martin - Inventory: 0.",
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void test_performOperation_someBooksAvailable(){
        IAddBook addBook = new AddBook();
        System.setOut(new PrintStream(outputStreamCaptor));
        BookInfo bookInfo = new BookInfo("Clean Code","Robert C. Martin");
        addBook.addBook("Clean Code","Robert C. Martin",1);
        String[] args = new String[3];
        args[0] = "search";
        args[1] = "Clean Code";
        args[2] = "Robert C. Martin";
        searchBook.performOperation(args);
        Assert.assertEquals("Clean Code - Robert C. Martin - Inventory: 1.",
                outputStreamCaptor.toString().trim());
        //Teardown
        IDeleteBook deleteBook = new DeleteBook();
        deleteBook.deleteBook(bookInfo.getName(), bookInfo.getAuthor());
    }


}
