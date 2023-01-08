package org.web.controllers.post;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.dao.inter.PostDAO;
import org.example.dao.inter.TopicDAO;
import org.example.dao.inter.UserDAO;
import org.example.model.Post;
import org.example.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.web.forms.PostForm;

import java.util.List;

@Controller
@RequestMapping(value={"/post"})
public class PostController {

    @Autowired
    TopicDAO topicDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    PostDAO postDAO;

    @GetMapping(value = {"/add"})
    public ModelAndView preAddPost(){
        return new ModelAndView("/addPost").addObject("postAddForm", new PostForm());
    }

    @PostMapping(value = {"/add"})
    public ModelAndView addPost(@ModelAttribute("postAddForm") PostForm postAddForm, HttpServletRequest request){
        ModelAndView modelAndView;
        HttpSession session = request.getSession(false);
        int userId = (int)session.getAttribute("id");
        int topicId = (int) session.getAttribute("topicId");

        postDAO.getPostByUserTopic(userId,topicId);

        if (!postAddForm.getName().equals("")) {
            if (!postAddForm.getText().equals("")) {
                Post post = new Post();
                post.setName(postAddForm.getName());
                post.setText(postAddForm.getText());
                post.setUser(userDAO.getById(userId));
                post.setTopic(topicDAO.getById(topicId));
                postDAO.add(post);
                modelAndView = new ModelAndView("myPost").addObject("addPost","<p style = \"color: blue\"> Post added successfully.</p>");
            }else{
                modelAndView = new ModelAndView("myPost").addObject("addPost","<p style = \"color: red\"> Post is not added, you can not add a post without text.</p>");
            }
        }else{
            modelAndView = new ModelAndView("myPost").addObject("addPost","<p style = \"color: red\"> Post not added, you can not add a post without a name.</p>");
        }
       return modelAndView;
    }

    @GetMapping(value = {"/delete"})
    public ModelAndView delete(@ModelAttribute("id") int postId){
        ModelAndView modelAndView;
        if (postId!=0){
            postDAO.delete(postId);
        }
        if (postDAO.getById(postId)==null){
            modelAndView = new ModelAndView("welcome").
                    addObject("deletePost","<p style = \"color: blue\"> Post successfully deleted.</p>");
        }else{
            modelAndView = new ModelAndView("welcome").
                    addObject("deletePost","<p style = \"color: red\"> Post not deleted.</p>");
        }
        return modelAndView;
    }
    
    @GetMapping(value = {"posts"})
    public ModelAndView myPosts(@ModelAttribute("idTopic") int idTopic, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        int userId = (int)session.getAttribute("id");
        session.setAttribute("topicId", idTopic);
        Topic topicById = topicDAO.getById(idTopic);
        List<Post> userPost = postDAO.getPostByUserTopic(userId,idTopic);

        return new ModelAndView("myPost")
                .addObject("topicName", topicById.getName())
                .addObject("posts",userPost)
                .addObject("topicId", session.getAttribute("topicId"));
    }

    @GetMapping(value = {"update"})
    public ModelAndView preUpdate(@ModelAttribute("idPost") int postId,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        session.setAttribute("postId", postId);
        PostForm postById = new PostForm(postDAO.getById(postId));
        return new ModelAndView("updatePost")
                .addObject("postForm", postById);
    }

    @PostMapping(value = {"update"})
    public ModelAndView update(@ModelAttribute("postForm") PostForm postUpdateForm,HttpServletRequest request){
        ModelAndView modelAndView;
        HttpSession session = request.getSession(false);
        int postId = (int)session.getAttribute("postId");


        Post postById = postDAO.getById(postId);
        postById.setName(postUpdateForm.getNewName());
        postById.setText(postUpdateForm.getNewText());

        postDAO.update(postById);

        if (postDAO.getById(postId).getName().equalsIgnoreCase(postUpdateForm.getNewName())){
            modelAndView = new ModelAndView("welcome")
                    .addObject("updatePost","<p style =\"color: blue\"> Post successfully updated</p>");
        }else{
            modelAndView = new ModelAndView("welcome")
                    .addObject("updatePost","<p style =\"color: red\"> Post not updated</p>");

        }
        return modelAndView;
    }

}
