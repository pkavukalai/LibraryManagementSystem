package com.library.managementsystem.operations.user.search;

import com.library.managementsystem.entities.book.BookInfo;
import com.library.managementsystem.operations.IOperation;

public interface ISearchBook extends IOperation {
    Integer searchBook(BookInfo bookInfo);
}
