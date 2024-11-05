package com.library.managementsystem.repositories.inventory;

import com.library.managementsystem.entities.book.Book;
import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.entities.book.BookStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author pkavukalai
 *
 */
public class InventoryRepository {

    public static TreeMap<BookInfo, List<Integer>> availableBookInfoToBookIds = new TreeMap<>();
    public static TreeMap<BookInfo, List<Integer>> borrowedBookInfoToBookIds = new TreeMap<>();

    //Read operations
    public static TreeMap<BookInfo, List<Integer>> getAllAvailableBooks() {
        return availableBookInfoToBookIds;
    }

    public static List<Integer> getAvailableBooks(BookInfo bookInfo) {
        return availableBookInfoToBookIds.get(bookInfo);
    }

    public static Integer getCountOfAvailableBooks(BookInfo bookInfo) {
        return null != availableBookInfoToBookIds.get(bookInfo) ?
                availableBookInfoToBookIds.get(bookInfo).size() : 0;
    }

    public static List<Integer> getBorrowedBooks(BookInfo bookInfo) {
        return borrowedBookInfoToBookIds.get(bookInfo);
    }

    // Create entry in inventory
    public static void addBooksToInventory(BookInfo bookInfo, List<Integer> bookIds) {
        if(availableBookInfoToBookIds.containsKey(bookInfo)){
            availableBookInfoToBookIds.get(bookInfo).addAll(bookIds);
        }else{
            availableBookInfoToBookIds.put(bookInfo,bookIds);
        }
    }

    // Update Inventory
    public static void updateInventory(Book book){
        //Get or Construct available book Ids List for the passed BookInfo
        List<Integer> availBookIds;
        if(availableBookInfoToBookIds.containsKey(book.getBookInfo())){
            availBookIds = availableBookInfoToBookIds.get(book.getBookInfo());
        }else{
            availBookIds = new ArrayList<>();
        }
        //Get or Construct borrowed book Ids List for the passed BookInfo
        List<Integer> borrowedBookIds;
        if(borrowedBookInfoToBookIds.containsKey(book.getBookInfo())){
            borrowedBookIds = borrowedBookInfoToBookIds.get(book.getBookInfo());
        }else{
            borrowedBookIds = new ArrayList<>();
        }
        //If book is returned - remove from borrow and add it to available list
        if(BookStatus.AVAILABLE.equals(book.getStatus())){
            availBookIds.add(book.getId());
             borrowedBookIds.remove(book.getId());
        }//If book is borrowed - remove from available and add it to borrow list
        if(BookStatus.BORROWED.equals(book.getStatus())){
            borrowedBookIds.add(book.getId());
            availBookIds.remove(book.getId());
        }
        //Update available and borrowed book info
        if(availBookIds.size() == 0){
            availableBookInfoToBookIds.remove(book.getBookInfo());
        }else{
            availableBookInfoToBookIds.put(book.getBookInfo(),availBookIds);
        }

        if(borrowedBookIds.size() == 0){
            borrowedBookInfoToBookIds.remove(book.getBookInfo());
        }else{
            borrowedBookInfoToBookIds.put(book.getBookInfo(),borrowedBookIds);
        }
    }

    // Delete Inventory
    public static void deleteInventory(BookInfo bookInfo){
        availableBookInfoToBookIds.remove(bookInfo);
    }

}