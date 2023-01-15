package org.web.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.User;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm{
    private int id;
    private String username;
    private String newUsername;
    private String password;
    private String email;
    private String role;
    private String repeatPassword;
    private String newPassword;
    private String newEmail;
    private MultipartFile fileData;
    private byte[] image;

    public UserForm(User user) {
        this.password = user.getPassword();
        this.username = user.getUserName();
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.image = user.getImage();
    }
}
