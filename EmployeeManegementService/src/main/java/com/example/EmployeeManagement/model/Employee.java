package com.example.EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(name = "isDeleted")
    private Boolean isDeleted;

    @Column(name = "isVerified")
    private Boolean isVerified;

    @JoinColumn(name = "department_id")
    @ManyToOne
    private Department department_emp;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(
                    name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "project_id", referencedColumnName = "id"))
    private List<Project> projects;

    @OneToMany(mappedBy = "employee")
    private List<PerformanceReview> performanceReviewsReceived = new ArrayList<>();

    @OneToMany(mappedBy = "reviewer")
    private List<PerformanceReview> performanceReviewsGiven = new ArrayList<>();

    public void addProject(Project project){
        projects.add(project);
    }

}