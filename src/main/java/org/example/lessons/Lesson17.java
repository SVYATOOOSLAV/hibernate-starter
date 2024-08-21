package org.example.lessons;

import org.example.entity.*;
import org.hibernate.SessionFactory;

import java.time.LocalDate;

public class Lesson17 implements StartAble{
    @Override
    public void start(SessionFactory sessionFactory) {
        Company company = Company.builder()
                .id(3)
                .name("Amazon")
                .build();

        var user = User.builder()
                .username("oleg@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Oleg")
                        .lastname("Kostin")
                        .birthDate(new Birthday(LocalDate.of(2001, 4, 23)))
                        .build())
                .role(Role.ADMIN)
                .company(company)
                .build();

        /* Сессии обычно открываются на уровне сервисов */

        try(var session = sessionFactory.openSession()){
            session.beginTransaction();

            //session.saveOrUpdate(company);

            //session.saveOrUpdate(user);

            var testUser = session.get(User.class, 5);
            lazyInitTest(testUser);

            session.getTransaction().commit();
        }
    }

    private void lazyInitTest(User testUser){
        System.out.println(testUser); // здесь еще не произошло внедрение company в user из-за того, что мы убрали из toString() company
        System.out.println(
                testUser
                        .getCompany()
                        .getName()
        );
        /* произошел select в company, внедрение в user.
         Участвует при этом HibernateProxy.
          Работает только в рамках session & transaction иначе будет LazyInitialException, так как источника уже нет */
    }
}
