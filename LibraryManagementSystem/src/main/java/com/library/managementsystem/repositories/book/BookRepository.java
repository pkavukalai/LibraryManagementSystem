package com.library.managementsystem.repositories.book;

import com.library.managementsystem.entities.book.Book;
import com.library.managementsystem.entities.book.BookInfo;

import java.util.TreeMap;

public class BookRepository {
    public static TreeMap<Integer,Book> books = new TreeMap<>();

    public static Book read(Integer id){
        if(books.containsKey(id)){
            return books.get(id);
        }
        return null;
    }

    public static Book insert(BookInfo bookInfo){
        Integer id = getLastId() + 1;
        Book book = Book.Builder.newInstance().
                setBookInfo(bookInfo).build();
        book.setId(id);
        books.put(id,book);
        return book;
    }

    private static Integer getLastId() {
        if(books.size() > 0){
            return books.lastKey();
        }
        return 0;
    }

    public static boolean update(Book book){
        if(books.containsKey(book.getId())){
            books.put(book.getId(),book);
            return true;
        }
        return false;
    }

    public static boolean delete(Integer bookId){
        if(books.containsKey(bookId)){
            books.remove(bookId);
            return true;
        }
        return false;
    }

}
