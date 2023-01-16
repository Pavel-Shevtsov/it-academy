package org.web.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Topic;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicForm {
    private long id;
    private String topicName;


    public TopicForm(Topic topic){
        this.id = topic.getId();
        this.topicName= topic.getName();
    }
}
