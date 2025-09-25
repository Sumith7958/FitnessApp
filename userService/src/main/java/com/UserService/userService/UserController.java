package com.UserService.userService;

import com.UserService.userService.Dto.ResponseUser;
import com.UserService.userService.Dto.UserDetails;
import com.UserService.userService.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserService UserService;

    @PostMapping("/Signup")
    public ResponseEntity<ResponseUser> signup(@Valid @RequestBody UserDetails userDetails){
        return UserService.signup(userDetails);
    }

    @GetMapping("/user/getdetails/{id}")
    public ResponseEntity<ResponseUser> getUserDetails(@PathVariable int id){
        return UserService.getuserdetails(id);
    }

}
