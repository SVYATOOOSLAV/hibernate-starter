package org.example;

import org.example.converter.BirthdayConverter;
import org.example.entity.Birthday;
import org.example.entity.Role;
import org.example.entity.User;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure(); // конфигурируем то, что указали в xml
        configuration.addAnnotatedClass(User.class); // добавляем User
        configuration.addAttributeConverter(new BirthdayConverter(), true);

        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var user = User.builder()
                    .username("ivan@mail.ru")
                    .firstname("Ivan")
                    .lastname("Ivanov")
                    .birthDate(new Birthday(LocalDate.of(2001, 4, 23)))
                    .role(Role.ADMIN)
                    .build();
            var userFromDB = session.get(User.class, user.getUsername());
            System.out.println(userFromDB);
            //session.save(user);

            //session.update(user);
            //session.saveOrUpdate(user);

            //session.delete(user);

            session.getTransaction().commit();
        }
    }
}
