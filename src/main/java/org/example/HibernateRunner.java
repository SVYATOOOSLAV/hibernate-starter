package org.example;

import org.example.lessons.Lesson15;
import org.example.lessons.Lesson16;
import org.example.util.HibernateUtil;
import org.hibernate.SessionFactory;

public class HibernateRunner {

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new HibernateUtil().buildSessionFactory()) {
          new Lesson15().start(sessionFactory);
          //new Lesson16().start(sessionFactory);
        }
    }
}


