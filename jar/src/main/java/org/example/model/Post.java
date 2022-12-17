package org.example.model;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "post",uniqueConstraints = @UniqueConstraint(name = "user_topic_post",columnNames = {"id","topic_id","user_id"}))
@NamedQueries({
        @NamedQuery(name = "Post.getAll", query = "SELECT p from Post p"),
        @NamedQuery(name = "Post.getPostByName", query = "SELECT p from Post p where p.name = :name"),
        @NamedQuery(name = "Post.getTopicById", query = "SELECT p from Post p where p.id = :id"),
        @NamedQuery(name = "Post.getByUserTopic",query = "SELECT p from Post p where p.topic.id = :idTopic and p.user.id = :idUser")
})
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true,nullable = false,length = 30)
    private String name;
    @Column(unique = true,nullable = false)
    private String text;
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
