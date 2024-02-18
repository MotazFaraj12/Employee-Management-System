package com.example.EmployeeManagement.security.service;

import com.example.EmployeeManagement.mapper.AuthUserMapper;
import com.example.EmployeeManagement.model.Employee;
import com.example.EmployeeManagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository repository;
    private final AuthUserMapper mapper;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> employee = repository.findByEmail(email);
        if (employee.isPresent()) {
            return mapper.EmployeeToAuthUser(employee.get());
        }
        else {
            throw new UsernameNotFoundException("User not found");
        }
    }



}
