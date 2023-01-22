package org.web.controllers.post;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.model.Post;
import org.example.model.Topic;
import org.example.model.User;
import org.example.repository.PostJpaRepository;
import org.example.repository.TopicJpaRepository;
import org.example.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.web.forms.PostForm;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value={"/post"})
public class PostController {

    @Autowired
    TopicJpaRepository topicJpaRepository;

    @Autowired
    PostJpaRepository postJpaRepository;


    @GetMapping(value = {"/addPost"})
    public ModelAndView preAddPost(){
        return new ModelAndView("addPost").addObject("postAddForm", new PostForm());
    }

    @PostMapping(value = {"/add"})
    public ModelAndView addPost(@ModelAttribute("postAddForm") PostForm postAddForm, HttpServletRequest request){
        ModelAndView modelAndView;
        HttpSession session = request.getSession(false);
        User sessionUser = (User) session.getAttribute("user");
        int topicId = (int) session.getAttribute("topicId");

        if (!postAddForm.getName().equals("")) {
            if (!postAddForm.getText().equals("")) {
                Post post = new Post();
                post.setName(postAddForm.getName());
                post.setText(postAddForm.getText());
                post.setUser(sessionUser);
                post.setTopic(topicJpaRepository.findById(topicId));
                postJpaRepository.save(post);
                modelAndView = new ModelAndView("addPost").addObject("addPost","<p style = \"color: blue\"> Post added successfully.</p>");
            }else{
                modelAndView = new ModelAndView("addPost").addObject("addPost","<p style = \"color: red\"> Post is not added, you can not add a post without text.</p>");
            }
        }else{
            modelAndView = new ModelAndView("addPost")
                    .addObject("addPost","<p style = \"color: red\"> Post not added, you can not add a post without a name.</p>")
                    .addObject("postAddForm", postAddForm);;
        }
       return modelAndView;
    }

    @GetMapping(value = {"/delete"})
    public ModelAndView delete(@RequestParam("id") int postId,HttpServletRequest request,HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = null;
        if (postId!=0){
            Post postById = postJpaRepository.findPostById(postId);
            postJpaRepository.delete(postById);
            if (postJpaRepository.findPostById(postId)==null){
                response.sendRedirect(request.getContextPath() + "/welcome");
            }else{
                modelAndView = new ModelAndView("welcome").
                        addObject("deletePost","<p style = \"color: red\"> Post not deleted.</p>");
            }
        }
        return modelAndView;
    }
    
    @GetMapping(value = {"/posts"})
    public ModelAndView myPosts(@RequestParam("id") int idTopic, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        User sessionUser = (User) session.getAttribute("user");
        session.setAttribute("topicId", idTopic);
        Topic topicById = topicJpaRepository.findById(idTopic);
        List<Post> userPost = postJpaRepository.findPostByUserTopic(sessionUser.getId(),idTopic);
        return new ModelAndView("myPost")
                .addObject("topicName", topicById.getName())
                .addObject("posts",userPost)
                .addObject("topicId", session.getAttribute("topicId"));
    }

    @GetMapping(value = {"update"})
    public ModelAndView preUpdate(@RequestParam("id") int postId,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        session.setAttribute("postId", postId);
        PostForm postById = new PostForm(postJpaRepository.findPostById(postId));
        return new ModelAndView("updatePost")
                .addObject("postForm",postById);
    }

    @PostMapping(value = {"update"})
    public ModelAndView update(@ModelAttribute("postForm") PostForm postUpdateForm, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = null;
        HttpSession session = request.getSession(false);
        Post postById = postJpaRepository.findPostById((Integer) session.getAttribute("postId"));
        if (!postUpdateForm.getNewName().equals("")) {
            postById.setName(postUpdateForm.getNewName());
        }
        if (!postUpdateForm.getNewText().equals("")) {
            postById.setText(postUpdateForm.getNewText());
        }
        postJpaRepository.save(postById);
        if (postJpaRepository.findPostById((Integer) session.getAttribute("postId")).getName().equalsIgnoreCase(postUpdateForm.getNewName())){
           response.sendRedirect(request.getContextPath()+"/welcome");
        }else{
            modelAndView = new ModelAndView("updatePost")
                    .addObject("updatePost","<p style =\"color: red\"> Post not updated</p>")
                    .addObject("postForm",postUpdateForm);
        }
        return modelAndView;
    }

}
