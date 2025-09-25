package com.UserService.userService.Dto;

import com.UserService.userService.Model.UserModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class UserDetails {
    @NotBlank(message ="Email should not be blank")
    @Email(message ="Invalid email format")
    private String email;
    private String name;
    @NotBlank(message ="Password should not be blank")
    @Size(min = 7,message = "Min 6 char")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
