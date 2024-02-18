package com.example.EmployeeManagement.controller;

import com.example.EmployeeManagement.DTO.*;
import com.example.EmployeeManagement.mapper.AuthUserMapper;
import com.example.EmployeeManagement.mapper.EmployeeMapper;
import com.example.EmployeeManagement.model.Employee;
import com.example.EmployeeManagement.security.model.AuthUser;
import com.example.EmployeeManagement.service.EmployeeService;
import com.example.EmployeeManagement.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Employees")
@RequiredArgsConstructor
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService service;
    private final JwtService jwtService;
    private final EmployeeMapper mapper;
    private final AuthUserMapper authUserMapper;
    //create and login services
    @PostMapping("/auth/register")
    public ResponseEntity<String> saveEmployee(@RequestBody RegisterRequest request , HttpServletResponse response) {
        logger.debug("Attempting to create a user with email: {}", request.getEmail());
        Employee employee = mapper.RequerstToEmployee(request);
        employee = service.signup(employee);
        String token = CreateJWToken(employee ,response);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/auth/login")
    public ResponseEntity<AuthUser> authenticate(@RequestBody AuthenticationRequest request , HttpServletResponse response,@AuthenticationPrincipal AuthUser authUser) {
        logger.debug("Attempting login validation for email: {}", request.getEmail());
        Employee employee = service.authenticate(request.getEmail() , request.getPassword());
        String token = CreateJWToken(employee ,response);
        return ResponseEntity.ok(authUser);
    }
    @GetMapping( "/auth/register/confirm")
    public ModelAndView confirm(@RequestParam("token") String token) {
        return service.confirmToken(token);
    }
    //other services
    @GetMapping
    public ResponseEntity<List<Employee>> viewAllEmployee() {
        return ResponseEntity.ok(service.getAllEmployees());
    }
    @PutMapping("/{email}")
    public ResponseEntity<String> updateEmployee(@PathVariable String email,@RequestBody Employee employee) {
        service.updateEmployeeByEmail(email,employee);
        return ResponseEntity.ok(service.updateEmployeeByEmail(email,employee));
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String email) {
        service.deleteEmployeeByEmail(email);
        return ResponseEntity.ok("deleted successfully");
    }
    @GetMapping("/{email}")
    public ResponseEntity<Employee> FindEmployeeByEmail(@PathVariable String email){
        return ResponseEntity.ok(service.FindEmployeeByEmail(email));
    }
    @GetMapping("/Projects")
    public ResponseEntity<List<EmployeeProjection>> findEmployeesWithMultipleProjects(){
        return ResponseEntity.ok(service.findEmployeesWithMultipleProjects());
    }
    @GetMapping("/{email}/Projects")
    public ResponseEntity<List<projectEmployee>> findAllTheProjectsForEmployee(@PathVariable String email){
        return ResponseEntity.ok(service.findProjectsForEmployee(email));
    }
    @PutMapping("/{email}/Role")
    public ResponseEntity<String> ChangeRole(@PathVariable String email){
        return ResponseEntity.ok(service.changeRole(email));
    }
    @PutMapping("/{email}/Departments/{id}")
    public ResponseEntity<String> assignDepartment(@PathVariable String email , @PathVariable Long id){
        return ResponseEntity.ok(service.assignDepartment( email ,  id));
    }
    @PutMapping("/{email}/Projects/{id}")
    public ResponseEntity<String> assignProject(@PathVariable String email , @PathVariable Long id){
        return ResponseEntity.ok(service.assignProject(email,id));
    }
    @PostMapping("/{email1}/Reviews/{email2}")
    public ResponseEntity<String> addReview (@PathVariable String email1, @PathVariable String email2, @RequestBody Comments comments){
        return ResponseEntity.ok(service.addReview(email1,email2,comments.getComments()));
    }
    private String CreateJWToken(Employee employee , HttpServletResponse response){
        //extra claims
        Map<String, Object> map = new HashMap<>();
        map.put("id" , employee.getId());
        map.put("FirstName", employee.getFirstName());
        map.put("LastName", employee.getLastName());
        map.put("role" , employee.getRole());
        map.put("isDeleted" , employee.getIsDeleted());
        map.put("isVerified" , employee.getIsVerified());
        //extra claims
        String jwtToken = jwtService.generateToken(map,authUserMapper.EmployeeToAuthUser(employee));
        Cookie jwtCookie = new Cookie("jwtToken", jwtToken);
        jwtCookie.setPath("/cookie");
        jwtCookie.setMaxAge(24*60*60);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        response.addCookie(jwtCookie);
        return jwtToken;
    }
}