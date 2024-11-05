package com.library.managementsystem.operations.admin.delete.impl;

import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.operations.admin.delete.IDeleteBook;
import com.library.managementsystem.repositories.book.BookRepository;
import com.library.managementsystem.repositories.inventory.InventoryRepository;

import java.util.List;

/**
 * This deletes the book from inventory and book repositories
 */
public class DeleteBook implements IDeleteBook {

    @Override
    public String getOperationName() {
        return "delete";
    }

    @Override
    public boolean validateArguments(String[] arguments) {
        return arguments.length == 3;
    }

    @Override
    public void performOperation(String[] arguments) {

        String bookName = arguments[1];
        String authorName = arguments[2];
        BookInfo bookInfo = new BookInfo(bookName,authorName);

        if(isCurrentlyBorrowed(bookInfo)){
            System.out.println("Cannot delete book "+ bookName +
                    " because it is currently borrowed.");
            return;
        }
        this.deleteBook(bookName,authorName);
        Integer availableBookCount = InventoryRepository.getCountOfAvailableBooks(bookInfo);
        if(availableBookCount == 0){
            System.out.println("Book "+ bookName +
                    " is successfully deleted.");
        }else{
            System.out.println("Delete operation not successful!");
        }
    }

    @Override
    public void deleteBook(String bookName,String author) {
        BookInfo bookInfo = new BookInfo(bookName,author);
        List<Integer> bookIds = InventoryRepository.getAvailableBooks(bookInfo);
        for(Integer bookId : bookIds){
            BookRepository.delete(bookId);
        }
        InventoryRepository.deleteInventory(bookInfo);
    }

    private boolean isCurrentlyBorrowed(BookInfo bookInfo) {
        List<Integer> borrowedBooks = InventoryRepository.getBorrowedBooks(bookInfo);
        return null != borrowedBooks && borrowedBooks.size() > 0;
    }

}
