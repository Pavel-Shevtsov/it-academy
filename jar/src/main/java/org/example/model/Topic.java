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
@NamedQueries({
        @NamedQuery(name = "Topic.getAll", query = "SELECT t from Topic t"),
        @NamedQuery(name = "Topic.getTopicById", query = "SELECT t from Topic t where t.id = :id"),
        @NamedQuery(name = "Topic.getTopicByTopicName", query = "SELECT t from Topic t where t.name = :topicName"),

})
@Table(name = "topic")
public class Topic implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(unique = true,nullable = false,length = 30)
    private String name;
    @OneToMany(mappedBy = "topic", cascade = {CascadeType.PERSIST})
    @ToString.Exclude
    private List<Post> post;
    @ManyToMany(mappedBy = "topics", cascade = {CascadeType.PERSIST})
    @ToString.Exclude
    private List<User> users;


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Topic topic = (Topic) o;
        return Objects.equals(id,topic.id) ;
    }

}
