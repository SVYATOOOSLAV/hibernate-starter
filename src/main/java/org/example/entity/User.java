package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"company", "profile", "userChats"})
@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Embedded //необязательная аннотация
    private PersonalInfo personalInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id") // необязательно, так как мы соединяемся по id
    private Company company;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

}

/*

   Если бы мы хотели, чтобы компания сохранялась автоматически при сохранении пользователя,
   тогда у пользователя указываем на company @ManyToOne(cascade = CascadeType.ALL)

   @ManyToOne(optional = false)
   optional = false - при селекте происходит inner join иначе left join

   @ManyToOne(optional = false, fetch = FetchType.LAZY)
   Позволяет получать объект тогда, когда он будет необходим (произойдет селект и вернется значение)

    @ManyToMany
    @JoinTable(
            name = "users_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
*/