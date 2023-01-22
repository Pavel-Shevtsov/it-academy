package org.example.repository;

import org.example.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import java.util.List;

public interface PostJpaRepository extends BaseRepository<Post, Integer> {
    Post findPostByName(String name);
    @Query("SELECT p from Post p where p.topic.id = ?1 and p.user.id = ?2")
    List<Post> findPostByUserTopic(int userId, int topicId);
    Post findPostById(int Id);
}
