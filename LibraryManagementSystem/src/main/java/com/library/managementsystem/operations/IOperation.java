package com.library.managementsystem.operations;

public interface IOperation {
    String getOperationName();
    boolean validateArguments(String arguments[]);
    void performOperation(String arguments[]);

}
