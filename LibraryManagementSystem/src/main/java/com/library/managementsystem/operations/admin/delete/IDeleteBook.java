package com.library.managementsystem.operations.admin.delete;

import com.library.managementsystem.operations.IOperation;

public interface IDeleteBook extends IOperation {
    void deleteBook(String bookName,String author);
}
