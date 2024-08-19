package org.example.lessons;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.hibernate.SessionFactory;

@Slf4j
public class Lesson16 implements StartAble {

    public void start(SessionFactory sessionFactory) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var user1 = session.get(User.class, "ivan@mail.ru");
            user1.getPersonalInfo().setFirstname("Ivan");

            // session.flush(); // позволяет до коммита привести БД в соотвтсвии с PersistenceContext
            // session.clear(); // очистка кэша PersistenceContext полностью
            // session.evict(user1); // очистка конкретного объекта из кэша сессии
            // session.refresh(user1); // обновляет текущий объект из БД
            // session.merge(user1); // идея такая же как с flush(), создает новый объект в сессии или возвращает существующий
            // session.isDirty(); // проверяет, есть ли в java объектах какие-либо изменения, которых нет в БД

            /*
             Hibernate автоматически вызовет update (при закрытии транзакции, если объект изменился),
             при этом PersistenceContext должен быть заполнен (вызван метод get для текущего объекта)
            */

            log.info("Successfully got user with id = [{}] ", user1.getUsername());

            session.getTransaction().commit();
        }
    }
}
