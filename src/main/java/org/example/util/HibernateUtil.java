package org.example.util;

import org.example.converter.BirthdayConverter;
import org.example.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration();
        configuration.configure(); // конфигурируем то, что указали в xml
        configuration.addAnnotatedClass(User.class); // добавляем User
        configuration.addAttributeConverter(new BirthdayConverter(), true);

        return configuration.buildSessionFactory();
    }
}
