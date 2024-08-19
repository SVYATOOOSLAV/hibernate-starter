package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String street;
    private String language;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}

/* @JoinColumn(name = "id") или @PrimaryKeyJoinColumn */
