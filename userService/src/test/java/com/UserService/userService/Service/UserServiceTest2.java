package com.UserService.userService.Service;

import com.UserService.userService.Dto.UserDetails;
import com.UserService.userService.Model.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("UserServiceTest2 class tests")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class UserServiceTest2 {

    @Autowired
    private TestEntityManager entityManager;  // ✅ Use TestEntityManager

    private UserModel buildUser(String email, String firstName ,String Password) {
        UserModel user = new UserModel();
        user.setEmail(email);
        user.setName(firstName);
        user.setPassword(Password);
        return user;
    }

    @Test
    @DisplayName("Should save user when valid details are provided")
    void shouldSaveValidUser() {
        UserModel user = buildUser("test@example.com", "John", "Doe");

        entityManager.persistAndFlush(user);
        assertNotNull(user.getEmail());
    }

    @Test
    @DisplayName("Should fail when email is not unique")
    void shouldFailWhenEmailNotUnique() {
        UserModel user1 = buildUser("duplicate@example.com", "Alice", "Smith");
        UserModel user2 = buildUser("duplicate@example.com", "Bob", "Johnson");

        entityManager.persistAndFlush(user1);
        entityManager.persist(user2);
        assertThrows(DataIntegrityViolationException.class, () -> entityManager.flush());
    }
}
