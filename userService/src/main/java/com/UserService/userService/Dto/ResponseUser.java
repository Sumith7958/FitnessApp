package com.UserService.userService.Dto;

import com.UserService.userService.Model.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class ResponseUser {
    private String email;
    private String name;
    private String password;
    private UserType role = UserType.USER;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;



}
