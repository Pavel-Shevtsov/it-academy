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
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true,nullable = false,length = 30)
    private String name;
    @Column(unique = true,nullable = false)
    private String text;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
    @ManyToOne()
    @JoinColumn(name = "topic_id")
    private Topic topic;
}
