package com.UserService.userService.Controller;

import com.UserService.userService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activityservice")
public class ActivityServiceController {

    @Autowired
    UserService userService;

    @PostMapping("/validateuser")
    public ResponseEntity<Boolean> validateuser(@RequestBody String uid){
        return userService.validateuser(uid);
    }

    @GetMapping()
    public ResponseEntity<Boolean> hello(){
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
