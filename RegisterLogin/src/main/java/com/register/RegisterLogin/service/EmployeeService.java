package com.register.RegisterLogin.service;

import com.register.RegisterLogin.dto.EmployeeDTO;
import com.register.RegisterLogin.dto.LoginDTO;
import com.register.RegisterLogin.entity.Employee;
import com.register.RegisterLogin.repo.EmployeeRepo;
import com.register.RegisterLogin.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepo employeeRepo, PasswordEncoder passwordEncoder) {
        this.employeeRepo = employeeRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public String addEmployee(EmployeeDTO employeeDTO) {

        Employee employee = new Employee(
                employeeDTO.getEmployeeName(),
                employeeDTO.getEmail(),
                this.passwordEncoder.encode(employeeDTO.getPassword())
        );

        employeeRepo.save(employee);
        return employee.getEmployeeName();
    }

    public LoginResponse loginEmployee(LoginDTO loginDTO) {
        String msg = "";
        Employee employee1 = employeeRepo.findByEmail(loginDTO.getEmail());
        if (employee1 != null){
            String password = loginDTO.getPassword();
            String encodedPassword = employee1.getPassword();
            Boolean isPwdRight= passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight){
                Optional<Employee> employee = employeeRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (employee.isPresent()){
                    return new LoginResponse("Login Success", true);
                }else {
                    return new LoginResponse("Login Failed", false);
                }
            }else {
                return new LoginResponse("Password not match", false);
            }
        }else {
            return new LoginResponse("Email not exists", false);
        }
    }
}
