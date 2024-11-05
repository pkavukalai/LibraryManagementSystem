package com.library.managementsystem.operations.authentication.registration;

import com.library.managementsystem.operations.IOperation;

public interface IRegistration extends IOperation {
    boolean register(String role,String userName, String password);
}
