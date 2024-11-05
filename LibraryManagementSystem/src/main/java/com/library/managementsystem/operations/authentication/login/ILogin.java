package com.library.managementsystem.operations.authentication.login;

import com.library.managementsystem.operations.IOperation;

public interface ILogin extends IOperation {

    boolean login(String userName, String password);

}
