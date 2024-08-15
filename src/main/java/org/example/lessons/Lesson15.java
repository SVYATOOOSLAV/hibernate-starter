package org.example.lessons;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Birthday;
import org.example.entity.PersonalInfo;
import org.example.entity.Role;
import org.example.entity.User;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

@Slf4j
public class Lesson15 implements StartAble {

    public void start(SessionFactory sessionFactory) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var user = User.builder()
                    .username("igor2@mail.ru")
                    .personalInfo(PersonalInfo.builder()
                            .firstname("Igor")
                            .lastname("Ionov")
                            .birthDate(new Birthday(LocalDate.of(2001, 4, 23)))
                            .build())
                    .role(Role.ADMIN)
                    .build();

//           var userFromDB = session.get(User.class, user.getUsername());
//            var userFromDB2 = session.get(User.class, user.getUsername());
//            var userFromDB3 = session.get(User.class, user.getUsername());
//            log.info("Trying to save user with id = [{}]", user.getId());
            //session.save(user);

            //session.update(user);
            //session.saveOrUpdate(user);

            //session.delete(user);

            session.getTransaction().commit();
        }
    }
}
