package com.library.managementsystem.operations.user.search.impl;

import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.operations.user.search.ISearchBook;
import com.library.managementsystem.repositories.inventory.InventoryRepository;

import java.util.List;

/**
 * This allows the user to Search book from inventory
 * @author Pavya Kavukalai
 */
public class SearchBook implements ISearchBook {

    @Override
    public String getOperationName() {
        return "search";
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
        Integer bookCount = this.searchBook(bookInfo);
        System.out.println(bookName + " - " + authorName + " - " +
                "Inventory: " + bookCount +".");
    }

    @Override
    public Integer searchBook(BookInfo bookInfo) {
        List<Integer> availableBooks = InventoryRepository.getAvailableBooks(bookInfo);
        if(null != availableBooks){
            return availableBooks.size();
        }
        return 0;
    }
}
