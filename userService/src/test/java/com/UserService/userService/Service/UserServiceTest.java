package com.UserService.userService.Service;
import com.UserService.userService.Dto.ResponseUser;
import com.UserService.userService.Dto.UserDetails;
import com.UserService.userService.Model.UserModel;
import com.UserService.userService.UserRepo.UserRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceTest class tests")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class UserServiceTest {

    @BeforeAll
    static void called1(){
        System.out.println("Testing of methods started");
    }

    @BeforeEach
    void called(){
        System.out.println("Testing of method is done! On to next one!");
    }

    @Mock
    UserRepo repo;

    @InjectMocks
    UserService userService;

    @Nested
    @DisplayName("Sign-up method tests")
    class Signup{
        @Test
        @DisplayName("Should return CREATED when user is saved successfully")
        void shouldReturnCreatedWhenUserSaved() {
            //Given
            UserDetails userDetailsDto = new UserDetails();
            userDetailsDto.setEmail("test@example.com");
            userDetailsDto.setName("John");
            when(repo.save(any(UserModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

            //When
            ResponseEntity<ResponseUser> response = userService.signup(userDetailsDto);

            // Then
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals("InsertionDone!", response.getBody());
            verify(repo, times(1)).save(any(UserModel.class));
        }

        @Test
        @DisplayName("Should return FORBIDDEN when email/phone violates unique constraint")
        void shouldReturnForbiddenOnDuplicate() {
            // Given
            UserDetails userDetails = new UserDetails();
            userDetails.setEmail("duplicate@example.com");
            userDetails.setName("Jane");

            when(repo.save(any(UserModel.class)))
                    .thenThrow(new DataIntegrityViolationException("Unique constraint violation"));

            // When
            ResponseEntity<ResponseUser> response = userService.signup(userDetails);

            // Then
            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
            assertEquals("Email or Phone Number already Exits Please verify!", response.getBody());
        }

        @Test
        @DisplayName("Should return CONFLICT on unexpected exception")
        void shouldReturnConflictOnUnexpectedError() {
            // Given
            UserDetails userDetails = new UserDetails();
            userDetails.setEmail("error@example.com");
            userDetails.setName("Error");

            // Mock behavior → throw generic exception
            when(repo.save(any(UserModel.class)))
                    .thenThrow(new RuntimeException("DB connection lost"));

            // When
            ResponseEntity<ResponseUser> response = userService.signup(userDetails);

            // Then
            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            assertEquals("Something went wrong!", response.getBody());
        }
    }
}