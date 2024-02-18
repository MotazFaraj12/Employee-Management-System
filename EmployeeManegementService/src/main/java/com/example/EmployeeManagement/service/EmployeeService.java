package com.example.EmployeeManagement.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import com.example.EmployeeManagement.DTO.*;
import com.example.EmployeeManagement.aspect.LogMethod;
import com.example.EmployeeManagement.model.*;
import com.example.EmployeeManagement.repository.DepartmentRepository;
import com.example.EmployeeManagement.repository.EmployeeRepository;
import com.example.EmployeeManagement.repository.PerformanceReviewRepository;
import com.example.EmployeeManagement.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository ProjectRepository;
    private final PerformanceReviewRepository performanceReviewRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmployeeEmailsGenerator employeeEmailsGenerator;
    private final PasswordEncoder encoder;

    @LogMethod
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee signup(Employee employee){
        String email = employee.getEmail();
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);
        if(employeeOptional.isPresent()){
            throw new IllegalStateException("email already exists");
        }
        employee.setPassword(encoder.encode(employee.getPassword()));
        employee = employeeRepository.save(employee);
        ConfirmationToken confirmationToken = confirmationTokenService.createConfirmationToken(employee);
        employeeEmailsGenerator.sendConfirmationEmail(employee, confirmationToken);
        return employee;
    }

    @LogMethod
    public Employee authenticate(String email , String password) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);
        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            if(encoder.matches(password , employee.getPassword())){
                return employee;
            }
            else {
                throw new IllegalStateException("Password is incorrect");
            }
        }else {
            throw new IllegalStateException("Employee dose not exists");
        }
    }

    @Transactional
    @LogMethod
    public ModelAndView confirmToken(String token) {
        ModelAndView modelAndView = new ModelAndView();
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmationDate() != null) {
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpirationData();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        confirmationTokenService.setConfirmedAt(token);
        employeeRepository.enableAppUser(confirmationToken.getEmployee().getEmail());
        modelAndView.setViewName("confirmationPage.html");
        return modelAndView;
    }

    @LogMethod
    public void deleteEmployeeByEmail(String email) {
        employeeRepository.deleteByEmail(email);
    }
    @LogMethod
    public String updateEmployeeByEmail(String email, Employee updatedEmployee) {
        Optional<Employee> optionalUser = employeeRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            Employee employee = optionalUser.get();
            employee.setEmail(updatedEmployee.getEmail());
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setLastName(updatedEmployee.getLastName());
            employeeRepository.save(employee);
            return "Employee Updated successfully";
        } else {
            return "Employee not found";
        }
    }
    @LogMethod
    public Employee FindEmployeeByEmail(String email){
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        return employee.orElse(null);
    }
    @LogMethod
    public List<EmployeeProjection> findEmployeesWithMultipleProjects(){
        return employeeRepository.findEmployeesWithMultipleProjects();
    }
    @LogMethod
    public String changeRole(String email){
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        if(employee.isPresent()){
            Employee employee1 = employee.get();
            employee1.setRole(Roles.ADMIN);
            employeeRepository.save(employee1);
            return "success";
        }else {
            return "Employee dose not exists";
        }
    }
    @LogMethod
    public List<projectEmployee> findProjectsForEmployee(String email){
        return employeeRepository.projectsForEmployee(email);
    }
    @LogMethod
    public String assignDepartment(String email , Long ID){
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        Optional<Department> department = departmentRepository.findById(ID);
        if(employee.isPresent() && department.isPresent()){
            Employee employee1 = employee.get();
            Department department1 = department.get();
            employee1.setDepartment_emp(department1);
            employeeRepository.save(employee1);
            return "Employee:" + employee1.getEmail()+"has been added to department:"+ department1.getName();
        }else {
            return "either Employee or Department is not found";
        }
    }
    @LogMethod
    public String assignProject(String email , Long id){
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        Optional<Project> project = ProjectRepository.findById(id);
        if(employee.isPresent() && project.isPresent()){
            Employee employee1 = employee.get();
            Project project1 = project.get();
            employee1.addProject(project1);
            employeeRepository.save(employee1);
            return "Employee:"+employee1.getEmail()+"has been assigned to project:" + project1.getName();
        }else {
            return "either Employee or Project is not found";
        }
    }
    @LogMethod
    public String addReview(String email1 , String email2 , String comments){
        Optional<Employee> employee1 = employeeRepository.findByEmail(email1);
        Optional<Employee> employee2 = employeeRepository.findByEmail(email2);
        if (employee1.isPresent() && employee2.isPresent()){
            PerformanceReview review = PerformanceReview.builder()
                    .reviewer(employee2.get())
                    .employee(employee1.get())
                    .comments(comments)
                    .date(new Date())
                    .build();
            performanceReviewRepository.save(review);
            return "your review has been posted";
        }else {
            return "Employee not found";
        }
    }
}