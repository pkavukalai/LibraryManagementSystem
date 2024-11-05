package com.library.managementsystem.operations.user.returnbks;

import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.operations.IOperation;

public interface IReturnBook extends IOperation {
    boolean returnBook(BookInfo bookInfo);
}
