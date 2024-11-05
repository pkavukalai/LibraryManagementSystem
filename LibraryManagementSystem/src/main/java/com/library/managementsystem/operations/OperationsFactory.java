package com.library.managementsystem.operations;

import com.library.managementsystem.operations.admin.add.impl.AddBook;
import com.library.managementsystem.operations.admin.delete.impl.DeleteBook;
import com.library.managementsystem.operations.authentication.login.impl.Login;
import com.library.managementsystem.operations.authentication.registration.impl.Registration;
import com.library.managementsystem.operations.user.borrow.impl.BorrowBook;
import com.library.managementsystem.operations.user.returnbks.impl.ReturnBook;
import com.library.managementsystem.operations.user.search.impl.SearchBook;
import com.library.managementsystem.operations.display.impl.ListBooks;

/**
 * This class generates and returns the operation object for the passed operation
 * @author Pavya Kavukalai
 */
public class OperationsFactory {

    private static OperationsFactory instance = null;
    private OperationsFactory() { }

    public static synchronized OperationsFactory getInstance() {
        if ( instance == null ) {
            instance = new OperationsFactory();
        }
        return instance;
    }

    public IOperation getOperationObject(String operationName) {

        switch (operationName) {
            case "register":
                return new Registration();
            case "login":
                return new Login();
            case "add":
                return new AddBook();
            case "delete":
                return new DeleteBook();
            case "search":
                return new SearchBook();
            case "borrow":
                return new BorrowBook();
            case "return":
                return new ReturnBook();
            case "list":
                return new ListBooks();
            default:
                return null;
        }
    }

}
