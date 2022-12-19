package org.exemple.dao.impl;

import org.example.config.AppContext;
import org.example.dao.inter.PostDAO;
import org.example.model.Post;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostDAOTest {

    @Autowired
    PostDAO postDAO;

    public static final String TEST_POST_NAME="PostNameTest";
    public static final String TEST_POST_NEW_NAME = "PostNewName";
    public static final String TEST_POST_TEXT="PostTextTest";
    public static final String TEST_POST_NEW_TEXT="PostNewText";

    @BeforeAll
    public void setUpPost(){
        Post post = new Post();
        post.setText(TEST_POST_TEXT);
        post.setName(TEST_POST_NAME);
        postDAO.add(post);
    }

    @Test
    public void addPostTest(){
        Assertions.assertEquals(postDAO.getById(13).getName(),TEST_POST_NAME);
    }

    @Test
    public void updatePostTest(){
        Post post = postDAO.getById(13);
        post.setName(TEST_POST_NEW_NAME);
        post.setText(TEST_POST_NEW_TEXT);
        postDAO.update(post);
        Assertions.assertEquals(postDAO.getById(13).getName(),TEST_POST_NEW_NAME);
    }
    @AfterAll
    public void deletePostTest(){
        postDAO.delete(13);
        Assertions.assertNull(postDAO.getById(13));
    }
}
