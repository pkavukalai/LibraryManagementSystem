package com.library.managementsystem.operations.admin.add.impl;

import com.library.managementsystem.entities.book.Book;
import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.operations.admin.add.IAddBook;
import com.library.managementsystem.repositories.book.BookRepository;
import com.library.managementsystem.repositories.inventory.InventoryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * This performs the Add book operation and updates Inventory
 * @author Pavya Kavukalai
 */
public class AddBook implements IAddBook {

    @Override
    public String getOperationName() {
        return "add";
    }

    @Override
    public boolean validateArguments(String[] arguments) {
        if( arguments.length != 4){
            return false;
        }
        try{
            Integer.parseInt(arguments[3]);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    @Override
    public void performOperation(String[] arguments) {
       String bookName = arguments[1];
       String authorName = arguments[2];
       Integer bookCount = Integer.parseInt(arguments[3]);
       Integer inventoryBkCount = this.addBook(bookName, authorName, bookCount);
       if(inventoryBkCount == bookCount){
           System.out.println("Book " +
                   bookName + " by " + authorName + " " +
                   "added successfully, inventory: " + inventoryBkCount +".");
       }else if(inventoryBkCount > bookCount){
           System.out.println("Book " +
                   bookName + " inventory successfully updated, new inventory: "
                   + inventoryBkCount +".");
       }
       else{
           System.out.println("Add book operation not successful!");
       }
    }

    @Override
    public Integer addBook(String bookName, String author, Integer bookCount) {
        BookInfo bookInfo = new BookInfo(bookName,author);
        createBookEntries(bookInfo, bookCount);
        Integer availableBookCount = InventoryRepository.getCountOfAvailableBooks(bookInfo);
        return availableBookCount;
    }

    private List<Integer> createBookEntries(BookInfo bookInfo, Integer bookCount) {
        List<Integer> bookIds = new ArrayList<>();
        int pointer = 0;
        while(pointer < bookCount){
            Book book = BookRepository.insert(bookInfo);
            bookIds.add(book.getId());
            pointer++;
        }
        //Update Inventory whenever entries are made to Book table. With DB, we can use triggers.
        InventoryRepository.addBooksToInventory(bookInfo,bookIds);
        return bookIds;
    }
}
