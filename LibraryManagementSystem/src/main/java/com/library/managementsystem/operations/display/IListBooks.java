package com.library.managementsystem.operations.display;

import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.operations.IOperation;

import java.util.TreeMap;

public interface IListBooks extends IOperation {
   TreeMap<BookInfo, Integer> listBookInfo();
}
