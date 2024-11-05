package com.library.managementsystem.repositories;

import com.library.managementsystem.entities.book.Book;
import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.entities.book.BookStatus;
import com.library.managementsystem.repositories.book.BookRepository;
import org.junit.Assert;
import org.junit.Test;

public class BookRepositoryTest {

    @Test
    public void test_bookRepository_CRUD(){
        String bookName = "Bob";
        String authorName = "password2";
        BookInfo bookInfo = new BookInfo(bookName,authorName);
        //Inserting new data
        Book book = BookRepository.insert(bookInfo);
        Assert.assertNotNull(book);
        //Inserting another book with same info
        Book anotherBook = BookRepository.insert(bookInfo);
        Assert.assertNotNull(anotherBook);
        //Ids of both books are different
        Assert.assertNotSame(book.getId(),anotherBook.getId());

        //reading data from repo
        Assert.assertNotNull(BookRepository.read(book.getId()));
        //reading data which is not in repo
        Assert.assertNull(BookRepository.read(1000));

        //Updating valid data
        book.setStatus(BookStatus.BORROWED);
        Assert.assertTrue(BookRepository.update(book));
        //Updating data which is not in repo
        Book invalidBook = Book.Builder.newInstance().
                setBookInfo(bookInfo).build();
        invalidBook.setId(20);
        Assert.assertFalse(BookRepository.update(invalidBook));

        //Deleting valid data
        Assert.assertTrue(BookRepository.delete(book.getId()));
        Assert.assertTrue(BookRepository.delete(anotherBook.getId()));
        Assert.assertFalse(BookRepository.delete(20));
    }

}
