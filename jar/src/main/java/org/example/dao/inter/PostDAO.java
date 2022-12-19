package org.example.dao.inter;
import org.example.model.Post;

import java.util.List;

public interface PostDAO extends DAO<Post, Integer> {
    List <Post> getPostByUserTopic(int userId, int topicId);
}
