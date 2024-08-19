package org.example;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Cleanup;
import org.example.entity.*;
import org.example.util.HibernateUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {

    /*@Test
    public void testHibernateApi() throws SQLException, IllegalAccessException {
        var user = User.builder()
                .username("ivan1@mail.ru")
                .firstname("Ivan")
                .lastname("Ivanov")
                .birthDate(new Birthday(LocalDate.of(2001, 4, 23)))
                .build();
        var sql = """
                insert into
                %s
                (%s)
                values
                (%s)
                """;

        var tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(annotation -> annotation.schema() + "." + annotation.name())
                .orElse(user.getClass().getName());

        Field[] fields = user.getClass().getDeclaredFields();

        var columnNames = Arrays.stream(fields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(Collectors.joining(", "));

        var columnValues = Arrays.stream(fields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "postgres");

        PreparedStatement preparedStatement = connection.prepareStatement(
                sql.formatted(tableName, columnNames, columnValues)
        );

        for(int i = 0; i < fields.length; i++){
            fields[i].setAccessible(true);
            preparedStatement.setObject(i + 1, fields[i].get(user)); // Object так как разные типы данных
        }

        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();

    }*/

    @Test
    public void checkOneToMany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var company = session.get(Company.class, 1);

        for(User user : company.getUsers()){
            System.out.println(user);
        }

        session.getTransaction().commit();
    }

    @Test
    public void addNewUserAndCompany(){
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        Company company = Company.builder()
                .id(4)
                .name("VK")
                .build();

        User user = User.builder()
                .username("oleg5@mail.ru")
                .build();

        session.beginTransaction();

        company.addUser(user);

        session.update(company);

        session.getTransaction().commit();
    }

    @Test
    public void checkOrphanRemoval(){
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var company = session.get(Company.class, 4);

        company.getUsers().removeIf(user -> user.getId() == 7); // мы изменили список внутри Java, но не в БД

        session.getTransaction().commit(); // Удаляем пользователя в БД
    }

    @Test
    public void checkOneToOne(){
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        User user = User.builder()
                .username("maksim4@mail.ru")
                .build();

        Profile profile = Profile.builder()
                .language("RU")
                .street("Morskaya 5")
                .build();

        session.beginTransaction();

        session.save(user);
        profile.setUser(user);
        session.save(profile);

        session.getTransaction().commit();
    }

    @Test
    public void checkOneToOneGetUser(){
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        System.out.println(session.get(User.class, 13).getProfile());

        session.getTransaction().commit();
    }

    @Test
    public void checkManyToMany(){
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        User user = session.get(User.class, 13);
        Chat chat = session.get(Chat.class, 1);

        UserChat userChat = UserChat.builder()
                .createdBy(user.getUsername())
                .createdAt(Instant.now())
                .build();

        userChat.setChat(chat);
        userChat.setUser(user);

        session.save(userChat);

        session.getTransaction().commit();
    }
}