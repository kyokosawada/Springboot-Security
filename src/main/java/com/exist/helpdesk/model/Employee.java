package com.exist.helpdesk.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Min(18)
    private Integer age;

    @NotBlank
    private String address;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{7,15}$")
    private String phone;

    @NotBlank
    private String employmentStatus;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
