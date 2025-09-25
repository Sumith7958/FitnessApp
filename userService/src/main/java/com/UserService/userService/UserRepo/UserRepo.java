package com.UserService.userService.UserRepo;


import com.UserService.userService.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserModel, Integer> {

    public UserModel findByEmail(String email);
    public UserModel findByUid(String uid);
    public Boolean existsByUid(String uid);
}
