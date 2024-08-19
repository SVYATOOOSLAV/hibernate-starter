package org.example.homework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trainer")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private BaseInfo baseInfo;

    @OneToMany(mappedBy = "trainer")
    @Builder.Default
    private List<TrainerCourse> trainerCourses = new ArrayList<>();
}
