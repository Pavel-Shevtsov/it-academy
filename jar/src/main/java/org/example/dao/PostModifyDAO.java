package org.example.dao;
import org.example.model.Post;

import java.util.List;

public interface PostModifyDAO {
    void addPost(Post post);
    void deletePost(int id);
    void updatePost(Post oldPost,Post newPost);
    List<Post> allPost();
    Post getPostById(int id);
    List <Post> getByUserTopic(int userId, int topicId);
}
