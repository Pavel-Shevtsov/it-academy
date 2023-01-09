package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "SELECT u from User u"),
        @NamedQuery(name = "User.getUserByUserName", query = "SELECT u from User u where u.userName = :userName"),
        @NamedQuery(name = "User.getUserByPassword", query = "SELECT u from User u where u.password = :password"),
        @NamedQuery(name = "User.getUserByEmail", query = "SELECT u from User u where u.email = :email"),
        @NamedQuery(name = "User.getUserByIdWithTopic", query = "SELECT u from User u left join fetch u.topics where u.id = :userId")
})

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private int id;
    @Column(unique = true,nullable = false,length = 30)
    private String userName;
    @Column(nullable = false,length = 30)
    private String password;
    @Column(unique = true,nullable = false,length = 40)
    private String email;
    @Column(nullable = false,length = 6)
    private String role;

    @ManyToMany(targetEntity = Topic.class,cascade = {CascadeType.ALL})
    @ToString.Exclude
    @JoinTable(
            name ="User_Topic",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "topic_id")}
    )
    private List<Topic> topics;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST} )
    @ToString.Exclude
    private List<Post> posts;

    @Lob
    @Column(name = "image",columnDefinition="mediumblob")
    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, email, role);
    }
}
