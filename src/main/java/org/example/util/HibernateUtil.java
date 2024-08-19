package org.example.util;

import org.example.converter.BirthdayConverter;
import org.example.entity.*;
import org.example.homework.converter.LocalDateConverter;
import org.example.homework.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration();
        configuration.configure(); // конфигурируем то, что указали в xml
        configuration.addAnnotatedClass(User.class); // добавляем User
        configuration.addAnnotatedClass(Company.class);
        configuration.addAnnotatedClass(Profile.class);
        configuration.addAnnotatedClass(Chat.class);
        configuration.addAnnotatedClass(UserChat.class);
        configuration.addAttributeConverter(new BirthdayConverter(), true);

        return configuration.buildSessionFactory();
    }

    public static SessionFactory buildHomeTaskSessionFactory(){
        Configuration configuration = new Configuration();
        configuration.configure(); // конфигурируем то, что указали в xml
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(GradeStudent.class);
        configuration.addAnnotatedClass(Trainer.class);
        configuration.addAnnotatedClass(TrainerCourse.class);
        configuration.addAttributeConverter(new LocalDateConverter(), true);

        return configuration.buildSessionFactory();
    }
}
