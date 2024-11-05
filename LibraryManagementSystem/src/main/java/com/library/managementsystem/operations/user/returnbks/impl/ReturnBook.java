package com.library.managementsystem.operations.user.returnbks.impl;

import com.library.managementsystem.entities.book.Book;
import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.entities.book.BookStatus;
import com.library.managementsystem.entities.user.User;
import com.library.managementsystem.operations.user.returnbks.IReturnBook;
import com.library.managementsystem.repositories.book.BookRepository;
import com.library.managementsystem.repositories.inventory.InventoryRepository;
import com.library.managementsystem.repositories.user.UserRepository;
import com.library.managementsystem.session.SessionManagement;

import java.util.List;
import java.util.Optional;

/**
 * This allows the user to Return book and update the status of Book and
 * count of available books in inventory
 * @author Pavya Kavukalai
 */
public class ReturnBook implements IReturnBook {

    @Override
    public String getOperationName() {
        return "return";
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
        boolean status = this.returnBook(bookInfo);
        if(status){
            System.out.println("Book "+bookName+" successfully returned.");
        }else{
            System.out.println("Book not in borrowed list. Please check!");
        }
    }

    @Override
    public boolean returnBook(BookInfo bookInfo) {
        User user = SessionManagement.getUserFromSession();
        List<Book> borrowedBooks = user.getBorrowedBooks();
        Optional<Book> returnedBookInBorrowList = borrowedBooks.stream().
                filter(book ->
                book.getBookInfo().equals(bookInfo)).findFirst();

        if(returnedBookInBorrowList.isPresent()){
            Book book = returnedBookInBorrowList.get();
            user.getBorrowedBooks().remove(book);
            book.setStatus(BookStatus.AVAILABLE);
            BookRepository.update(book);
            InventoryRepository.updateInventory(book);
            UserRepository.update(user);
            return true;
        }
        return false;
    }
}
