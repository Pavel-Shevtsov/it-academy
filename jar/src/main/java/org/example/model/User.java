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


public class User implements Serializable {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private int id;
    @Column(unique = true,nullable = false,length = 30)
    private String userName;
    @Column(nullable = false,length = 70)
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

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL} )
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
