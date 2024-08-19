package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;

    @Builder.Default // В конструкторе будет лежать значение по умолчанию
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        user.setCompany(this);
    }
}

/* Использовать в качестве хранилища Set не лучшая идея,
 так как Set под капотом использует HashMap, HashMap использует Equals & HashCode,
 и получается у нас циклическая зависимость User и Company.
 Для решения надо переопределить Equals & HashCode @EqualsAndHashCode(of = "name")

    @OneToMany(mappedBy = "company") или @JoinColumn(name = "company_id")

    orphanRemoval = true будет следить за списком и отправлять соотвутвующие запросы (например удаление пользователя)
*/

