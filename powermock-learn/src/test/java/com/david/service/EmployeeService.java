package com.david.service;

import com.david.util.Employee;

/**
 * Created by haojk on 12/5/16.
 */
public class EmployeeService {

    public int getEmployeeCount() {
        return Employee.count();
    }
}
