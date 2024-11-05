package com.library.managementsystem.operations.admin.add;

import com.library.managementsystem.operations.IOperation;

public interface IAddBook extends IOperation {
    Integer addBook(String bookName, String author, Integer bookCount);
}
