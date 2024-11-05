package com.library.managementsystem.repositories;

import com.library.managementsystem.entities.book.Book;
import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.entities.user.User;
import com.library.managementsystem.entities.user.UserInfo;
import com.library.managementsystem.entities.user.UserRole;
import com.library.managementsystem.operations.user.borrow.IBorrowBook;
import com.library.managementsystem.operations.user.borrow.impl.BorrowBook;
import com.library.managementsystem.operations.user.returnbks.IReturnBook;
import com.library.managementsystem.operations.user.returnbks.impl.ReturnBook;
import com.library.managementsystem.repositories.book.BookRepository;
import com.library.managementsystem.repositories.inventory.InventoryRepository;
import com.library.managementsystem.repositories.user.UserRepository;
import com.library.managementsystem.session.SessionManagement;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class InventoryRepositoryTest {

    UserInfo userInfo = null;
    User user = null;
    IBorrowBook borrowBook = null;
    IReturnBook returnBook = null;

    @Before
    public void test_dataSetup(){
        userInfo = new UserInfo("Bob","password2");
        user = User.Builder.newInstance().setUserRole
                (UserRole.user).setUserInfo(userInfo).build();
        SessionManagement.saveUserInSession(user);
        borrowBook = new BorrowBook();
        returnBook = new ReturnBook();
    }


    @Test
    public void test_inventoryOperations_CRUD(){

        String bookName = "Clean Code";
        String authorName = "Robert C. Martin";
        BookInfo bookInfo = new BookInfo(bookName,authorName);

        //Read before adding book to inventory
        Assert.assertEquals(InventoryRepository.
                getCountOfAvailableBooks(bookInfo).intValue(),0);
        //Adding books to inventory
        List<Integer> bookIds = new ArrayList<>();
        Book book1 = BookRepository.insert(bookInfo);
        bookIds.add(book1.getId());
        InventoryRepository.addBooksToInventory(bookInfo,bookIds);
        //Read after adding books to inventory
        Assert.assertEquals(InventoryRepository.
                getCountOfAvailableBooks(bookInfo).intValue(),1);
        //Add 2 more books of same type
        List<Integer> anotherList = new ArrayList<>();
        Book book2 = BookRepository.insert(bookInfo);
        bookIds.add(book2.getId());
        Book book3 = BookRepository.insert(bookInfo);
        bookIds.add(book3.getId());
        InventoryRepository.addBooksToInventory(bookInfo,anotherList);
        //Read size after adding
        Assert.assertEquals(InventoryRepository.
                getAvailableBooks(bookInfo).size(),3);
        // Add new type of Book
        String newBookName = "Microservices Design Patterns";
        String newAuthorName = "William W";
        BookInfo newBookInfo = new BookInfo(newBookName,newAuthorName);
        List<Integer> newBookdIds = new ArrayList<>();
        Book newBook = BookRepository.insert(newBookInfo);
        newBookdIds.add(newBook.getId());
        InventoryRepository.addBooksToInventory(newBookInfo,newBookdIds);
        //Read inventory after adding new type of book
        Assert.assertEquals(InventoryRepository.
                getAllAvailableBooks().size(),2);

        //Borrow newBookInfo having only 1 book in inventory
        borrowBook.borrow(newBookInfo);
        //Read inventory after borrowing book.
        Assert.assertEquals(InventoryRepository.
                getAllAvailableBooks().size(),1);
        //Return new Book
        returnBook.returnBook(newBookInfo);
        //Read inventory after returning new type of book
        Assert.assertEquals(InventoryRepository.
                getAllAvailableBooks().size(),2);
        //Delete new type of book
        InventoryRepository.deleteInventory(newBookInfo);
        //Read inventory size after deleting
        Assert.assertEquals(InventoryRepository.
                getAllAvailableBooks().size(),1);

        //Borrow bookInfo having 3 books in inventory
        borrowBook.borrow(bookInfo);
         //read operation should show an increase in borrowed book list
        Assert.assertEquals(InventoryRepository.
                getBorrowedBooks(bookInfo).size(),1);
        //read operation should show a decrease in available book list
        Assert.assertEquals(InventoryRepository.getAvailableBooks
                (bookInfo).size(),2);
        //return book
        returnBook.returnBook(bookInfo);
        //read operation should show an empty borrowed book list
        Assert.assertEquals(InventoryRepository.
                getBorrowedBooks(bookInfo),null);
        //read operation should show an increase in available book list
        Assert.assertEquals(InventoryRepository.getAvailableBooks
                (bookInfo).size(),3);
        //Delete inventory
        InventoryRepository.deleteInventory(bookInfo);
        InventoryRepository.deleteInventory(newBookInfo);

        //Read operation should show an empty list
        Assert.assertEquals(InventoryRepository.
                getCountOfAvailableBooks(bookInfo).intValue(),0);
    }

    @After
    public void tearDown(){
        SessionManagement.clearUserFromSession();
        UserInfo userInfo = new UserInfo("Bob","password2");
        UserRepository.delete(userInfo);
        BookRepository.delete(1);
        BookRepository.delete(2);
        BookRepository.delete(3);
    }

}
