package com.library.managementsystem.operations;

import com.library.managementsystem.operations.admin.add.IAddBook;
import com.library.managementsystem.operations.admin.delete.IDeleteBook;
import com.library.managementsystem.operations.authentication.login.ILogin;
import com.library.managementsystem.operations.authentication.registration.IRegistration;
import com.library.managementsystem.operations.display.IListBooks;
import com.library.managementsystem.operations.user.borrow.IBorrowBook;
import com.library.managementsystem.operations.user.returnbks.IReturnBook;
import com.library.managementsystem.operations.user.search.ISearchBook;
import org.junit.Assert;
import org.junit.Test;

public class OperationsFactoryTest {

    @Test
    public void test_getOperationObject_search(){
        IOperation operation = OperationsFactory.getInstance().
                getOperationObject("search");
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof ISearchBook);
    }

    @Test
    public void test_getOperationObject_borrow(){
        IOperation operation = OperationsFactory.getInstance().
                getOperationObject("borrow");
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof IBorrowBook);
    }

    @Test
    public void test_getOperationObject_return(){
        IOperation operation = OperationsFactory.getInstance().
                getOperationObject("return");
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof IReturnBook);
    }

    @Test
    public void test_getOperationObject_list(){
        IOperation operation = OperationsFactory.getInstance().
                getOperationObject("list");
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof IListBooks);
    }

    @Test
    public void test_getOperationObject_register(){
        IOperation operation = OperationsFactory.getInstance().
                getOperationObject("register");
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof IRegistration);
    }

    @Test
    public void test_getOperationObject_login(){
        IOperation operation = OperationsFactory.getInstance().
                getOperationObject("login");
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof ILogin);
    }

    @Test
    public void test_getOperationObject_add(){
        IOperation operation = OperationsFactory.getInstance().
                getOperationObject("add");
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof IAddBook);
    }

    @Test
    public void test_getOperationObject_delete(){
        IOperation operation = OperationsFactory.getInstance().
                getOperationObject("delete");
        Assert.assertNotNull(operation);
        Assert.assertTrue(operation instanceof IDeleteBook);
    }

    @Test
    public void test_getOperationObject_invalidOperation(){
        IOperation operation = OperationsFactory.getInstance().
                getOperationObject("view");
        Assert.assertNull(operation);
    }

}


