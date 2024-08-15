package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    @Embedded //необязательная аннотация
    private PersonalInfo personalInfo;
    @Enumerated(EnumType.STRING)
    private Role role;
}
