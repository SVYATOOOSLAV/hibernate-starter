package org.example;

import lombok.Cleanup;
import org.example.homework.entity.Course;
import org.example.util.HibernateUtil;
import org.junit.jupiter.api.Test;

public class CheckH2Test {
    @Test
    public void checkWorkH2(){
        @Cleanup var sessionFactory = HibernateUtil.buildHomeTaskSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var course = Course.builder().name("Java").build();

        session.save(course);

        session.getTransaction().commit();
    }
}
