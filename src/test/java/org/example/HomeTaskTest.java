package org.example;

import lombok.Cleanup;
import org.example.homework.entity.*;
import org.example.util.HibernateUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

public class HomeTaskTest {


    @Test
    public void addNewStudentWithCourseTest() {
        @Cleanup var sessionFactory = HibernateUtil.buildHomeTaskSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        var course = Course.builder()
                .id(1)
                .name("Java Enterprise")
                .build();

        var student = Student.builder()
                .baseInfo(BaseInfo.builder()
                        .firstName("Aleksey")
                        .lastName("Skalkin")
                        .build())
                .course(course)
                .build();

        session.beginTransaction();

        //session.save(course);

        session.save(student);

        for (int j = 0; j < 10; j++) {
            student.getGrades().add(
                    GradeStudent.builder()
                            .student(student)
                            .grade(new Random().nextInt(11))
                            .build()
            );
        }

        session.getTransaction().commit();
    }

    @Test
    public void getAllStudentFromCourseTest() {
        @Cleanup var sessionFactory = HibernateUtil.buildHomeTaskSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var course = session.get(Course.class, 1);

        course.getStudents().forEach(System.out::println);

        session.getTransaction().commit();
    }


    @Test
    public void addGradesForStudent() {
        @Cleanup var sessionFactory = HibernateUtil.buildHomeTaskSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        Random random = new Random();

        session.beginTransaction();

        var student = session.get(Student.class, 5);
        for (int j = 0; j < 10; j++) {
            var grade = GradeStudent.builder()
                    .student(student)
                    .grade(random.nextInt(11))
                    .build();

            session.save(grade);
        }

//        var countGrades = 10;
//        for (int i = 1; i < 6; i++) {
//            var student = session.get(Student.class, i);
//            for (int j = 0; j < countGrades; j++) {
//                var grade = GradeStudent.builder()
//                        .student(student)
//                        .grade(random.nextInt(11))
//                        .build();
//
//                session.save(grade);
//            }
//        }

        session.getTransaction().commit();
    }

    @Test
    public void removeStudentsFromCourse() {
        @Cleanup var sessionFactory = HibernateUtil.buildHomeTaskSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var course = session.get(Course.class, 1);

        course.getStudents().removeIf(student -> {
            double sum = 0.0;
            var grades = student.getGrades();

            for (var grade : grades) {
                sum += grade.getGrade();
            }

            return sum / grades.size() < 6.0;
        });

        session.getTransaction().commit();
    }

    @Test
    public void removeCourse() {
        @Cleanup var sessionFactory = HibernateUtil.buildHomeTaskSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var course = session.get(Course.class, 1);

        session.delete(course);

        session.getTransaction().commit();
    }

    @Test
    public void createTrainerWithCourses() {
        @Cleanup var sessionFactory = HibernateUtil.buildHomeTaskSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        var trainer = Trainer.builder()
                .baseInfo(BaseInfo.builder()
                        .firstName("Konstantin")
                        .lastName("Melich")
                        .build())
                .build();

        session.beginTransaction();

        var courses = List.of(session.get(Course.class, 2), session.get(Course.class, 3));

        session.save(trainer);

        courses.forEach(course -> {
            var trainerCourse = TrainerCourse.builder().build();
            trainerCourse.setCourse(course);
            trainerCourse.setTrainer(trainer);

            session.save(trainerCourse);
        });

        session.getTransaction().commit();
    }

    @Test
    public void changeNameForCourse() {
        @Cleanup var sessionFactory = HibernateUtil.buildHomeTaskSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var course = session.get(Course.class, 3);
        course.setName("Java Magic");

        session.getTransaction().commit();

    }

    @Test
    public void getAllCoursesFromTrainer() {
        @Cleanup var sessionFactory = HibernateUtil.buildHomeTaskSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var trainer = session.get(Trainer.class, 1);

        trainer.getTrainerCourses().forEach(trainerCourse ->
                System.out.println(
                        trainerCourse.getCourse().getName()
                )
        );

        session.getTransaction().commit();

    }

    @Test
    public void getAllTrainersFromCourse() {
        @Cleanup var sessionFactory = HibernateUtil.buildHomeTaskSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var course = session.get(Course.class, 2);

        course.getTrainerCourses().forEach(trainerCourse ->
                System.out.println(
                        trainerCourse.getTrainer().getBaseInfo()
                )
        );

        session.getTransaction().commit();

    }

}
