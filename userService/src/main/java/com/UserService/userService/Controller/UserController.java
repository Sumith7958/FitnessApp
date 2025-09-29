package com.UserService.userService.Controller;

import com.UserService.userService.Dto.ResponseUser;
import com.UserService.userService.Dto.UserDetails;
import com.UserService.userService.Service.RedisService;
import com.UserService.userService.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService UserService;

    @Autowired
    private RedisService redisService;


    @PostMapping("/signup")
    public ResponseEntity<ResponseUser> signup(@Valid @RequestBody UserDetails userDetails){
        return UserService.signup(userDetails);
    }

    @GetMapping("/getdetails/{id}")
    public ResponseEntity<ResponseUser> getUserDetails(@PathVariable String id){

        ResponseUser a = redisService.get( id);
        if (a != null) {
            return new ResponseEntity<>(a, HttpStatus.ACCEPTED);
        } else {
            ResponseEntity<ResponseUser> response = UserService.getuserdetails(id);
            ResponseUser body = response.getBody();
            if (body != null) {
                redisService.set( id, body, 300L);
                a=redisService.get(id);
            }
            return new ResponseEntity<>(body,HttpStatus.OK);
        }
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

}
