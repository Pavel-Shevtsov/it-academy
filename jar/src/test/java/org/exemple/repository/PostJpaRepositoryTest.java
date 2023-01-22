package org.exemple.repository;

import org.example.config.TestAppContext;
import org.example.model.Post;
import org.example.repository.PostJpaRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestAppContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostJpaRepositoryTest {

    @Autowired
    PostJpaRepository postJpaRepository;

    public static final String TEST_POST_NAME="PostNameTest";
    public static final String TEST_POST_NEW_NAME = "PostNewName";
    public static final String TEST_POST_TEXT="PostTextTest";
    public static final String TEST_POST_NEW_TEXT="PostNewText";
    public static Integer TEST_POST_ID = null;

    @BeforeAll
    public void setUpPost(){
        Post post = new Post();
        post.setText(TEST_POST_TEXT);
        post.setName(TEST_POST_NAME);
        postJpaRepository.save(post);
    }

    @Test
    public void findPostByNameTest(){
        Post postByName = postJpaRepository.findPostByName(TEST_POST_NAME);
        Assertions.assertNull(postByName);
        TEST_POST_ID = postByName.getId();
    }

    @Test
    public void updatePostTest(){
        Post post = postJpaRepository.findPostById(TEST_POST_ID);
        post.setName(TEST_POST_NEW_NAME);
        post.setText(TEST_POST_NEW_TEXT);
        postJpaRepository.save(post);
        Assertions.assertEquals(postJpaRepository.findPostById(TEST_POST_ID).getName(),TEST_POST_NEW_NAME);
    }
    @AfterAll
    public void deletePostTest(){
        postJpaRepository.delete(postJpaRepository.findPostById(TEST_POST_ID));
        Assertions.assertNull(postJpaRepository.findPostById(TEST_POST_ID));
    }
}
