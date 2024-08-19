package org.example.homework.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(exclude = {"trainer", "course"})
@Table(name = "trainer_course")
public class TrainerCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public void setTrainer(Trainer trainer){
        this.trainer = trainer;
        trainer.getTrainerCourses().add(this);
    }

    public void setCourse(Course course){
        this.course = course;
        course.getTrainerCourses().add(this);
    }
}
