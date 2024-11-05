package com.library.managementsystem.operations.user.borrow;

import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.operations.IOperation;

public interface IBorrowBook extends IOperation {
    boolean borrow(BookInfo bookInfo);
}
