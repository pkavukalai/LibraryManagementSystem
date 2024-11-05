package com.library.managementsystem.operations.display.impl;

import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.operations.display.IListBooks;
import com.library.managementsystem.repositories.inventory.InventoryRepository;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * This lists the available books from inventory
 * @author Pavya Kavukalai
 */
public class ListBooks implements IListBooks {

    @Override
    public String getOperationName() {
        return "list";
    }

    @Override
    public boolean validateArguments(String[] arguments) {
        return arguments.length == 1;
    }

    @Override
    public void performOperation(String[] arguments) {
        TreeMap<BookInfo,Integer> bookToCountMap = this.listBookInfo();
        if(bookToCountMap.size() > 0){
            int serialNumber = 0;
            for(BookInfo bookInfo : bookToCountMap.keySet()){
                serialNumber = serialNumber + 1;
                Integer bookCount = bookToCountMap.get(bookInfo);
                System.out.println(serialNumber + ". " + bookInfo.getName()
                        + " - " + bookInfo.getAuthor() + " - " +
                        "Inventory: " + bookCount +".");
            }
        }else{
            System.out.println("No books available!");
        }
    }

    @Override
    public TreeMap<BookInfo, Integer> listBookInfo() {
        TreeMap<BookInfo, List<Integer>> bookInfoToBookIdsMap =
                InventoryRepository.getAllAvailableBooks();
        TreeMap<BookInfo,Integer> bookToCountMap = new TreeMap<>();
        for(Entry<BookInfo,List<Integer>> entry: bookInfoToBookIdsMap.entrySet()){
            BookInfo bookInfo = entry.getKey();
            Integer count = entry.getValue().size();
            bookToCountMap.put(bookInfo,count);
        }
        return bookToCountMap;
    }

}
