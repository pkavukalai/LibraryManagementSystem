package com.library.managementsystem.operations.user.borrow.impl;

import com.library.managementsystem.entities.book.Book;
import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.entities.book.BookStatus;
import com.library.managementsystem.entities.user.User;
import com.library.managementsystem.operations.user.borrow.IBorrowBook;
import com.library.managementsystem.repositories.book.BookRepository;
import com.library.managementsystem.repositories.inventory.InventoryRepository;
import com.library.managementsystem.repositories.user.UserRepository;
import com.library.managementsystem.session.SessionManagement;

import java.util.List;

/**
 * This allows the user to borrow book and update the status of Book and
 * count of available books in inventory
 * @author Pavya Kavukalai
 */
public class BorrowBook implements IBorrowBook {

    @Override
    public String getOperationName() {
        return "borrow";
    }

    @Override
    public boolean validateArguments(String[] arguments) {
        return arguments.length == 3;
    }

    @Override
    public void performOperation(String[] arguments) {
       String bookName = arguments[1];
       String authorName = arguments[2];
       BookInfo bookInfo = new BookInfo(bookName, authorName);
       boolean status = this.borrow(bookInfo);
       if(status){
          System.out.println("Book " + bookName + " successfully borrowed.");
       }else{
          System.out.println("Book not available to borrow!");
       }
    }

    @Override
    public boolean borrow(BookInfo bookInfo) {
        User user = SessionManagement.getUserFromSession();
        List<Integer> availableBookIds = InventoryRepository.getAvailableBooks(bookInfo);
        if(null != availableBookIds){
            Book book = BookRepository.read(availableBookIds.get(0));
            book.setStatus(BookStatus.BORROWED);
            user.getBorrowedBooks().add(book);
            BookRepository.update(book);
            UserRepository.update(user);
            InventoryRepository.updateInventory(book);
            return true;
        }
       return false;
    }
}
