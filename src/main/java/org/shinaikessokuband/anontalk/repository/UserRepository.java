package org.shinaikessokuband.anontalk.repository;

import org.shinaikessokuband.anontalk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByEmail(String email);
    List<User> findByUserId(Integer userId);
    List<User> findByPhoneNumber(String password);
    List<User> deleteByUserId(Integer userId);
    List<User> findByUsername(String username);
}
