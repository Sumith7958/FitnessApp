package com.UserService.userService.Service;

import com.UserService.userService.Dto.ResponseUser;
import com.UserService.userService.Dto.UserDetails;
import com.UserService.userService.Model.UserModel;
import com.UserService.userService.UserRepo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<ResponseUser> signup(UserDetails userDetails) {
        MDC.put("className", UserService.class.getSimpleName());
        try {
            UserModel userModel = new UserModel();
            userModel.setEmail(userDetails.getEmail());
            userModel.setName(userDetails.getName());
            userModel.setPassword(userDetails.getPassword());
            userRepo.save(userModel);
            UserModel temp=userRepo.findByEmail(userDetails.getEmail());
            ResponseUser responseUser=new ResponseUser();
            responseUser.setUid(temp.getUid());
            responseUser.setEmail(temp.getEmail());
            responseUser.setCreatedDateTime(temp.getCreatedDateTime());
            responseUser.setPassword(temp.getPassword());
            responseUser.setName(temp.getName());
            responseUser.setUpdatedDateTime(temp.getUpdatedDateTime());
            responseUser.setRole(temp.getRole());
            log.info("Data has been inserted to data base");
            return new ResponseEntity<>(responseUser, HttpStatus.CREATED);
        }catch(DataIntegrityViolationException exe){
            log.error("Data integration error : {}", exe.getMessage());
            return new ResponseEntity<>(new ResponseUser(),HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
            return new ResponseEntity<>(new ResponseUser(), HttpStatus.CONFLICT);
        }finally {
            MDC.remove("className");
        }
    }

    public ResponseEntity<ResponseUser> getuserdetails(String id) {

        UserModel temp=userRepo.findByUid(id);
        ResponseUser responseUser=new ResponseUser();
        responseUser.setUid(temp.getUid());
        responseUser.setEmail(temp.getEmail());
        responseUser.setCreatedDateTime(temp.getCreatedDateTime());
        responseUser.setPassword(temp.getPassword());
        responseUser.setName(temp.getName());
        responseUser.setUpdatedDateTime(temp.getUpdatedDateTime());
        responseUser.setRole(temp.getRole());
        return new ResponseEntity<>(responseUser,HttpStatus.ACCEPTED);

    }

    public ResponseEntity<Boolean> validateuser(String uid) {
        return new ResponseEntity<>(userRepo.existsByUid(uid),HttpStatus.OK);
    }
}
