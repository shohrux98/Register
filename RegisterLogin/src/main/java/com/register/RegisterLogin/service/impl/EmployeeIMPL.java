package com.register.RegisterLogin.service.impl;

import com.register.RegisterLogin.dto.EmployeeDTO;
import com.register.RegisterLogin.entity.Employee;
import com.register.RegisterLogin.repo.EmployeeRepo;
import com.register.RegisterLogin.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

public class EmployeeIMPL  {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


//    @Override
    public String addEmployee(EmployeeDTO employeeDTO) {

        Employee employee = new Employee(
                employeeDTO.getEmployeeName(),
                employeeDTO.getEmail(),
                this.passwordEncoder.encode(employeeDTO.getPassword())
        );

        employeeRepo.save(employee);
        return employee.getEmployeeName();
    }
}
