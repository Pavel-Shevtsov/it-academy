package org.web.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Post;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostForm {
    private int id;
    private String name;
    private String newName;
    private String text;
    private String newText;

    public PostForm(Post post){
        this.id = post.getId();
        this.name = post.getName();
        this.text = post.getText();
    }
}





