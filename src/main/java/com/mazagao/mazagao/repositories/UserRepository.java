package com.mazagao.mazagao.repositories;

import com.mazagao.mazagao.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    @Query("SELECT u FROM User u ORDER BY u.score DESC LIMIT 5")
    List<User> getScoreboard();


}
