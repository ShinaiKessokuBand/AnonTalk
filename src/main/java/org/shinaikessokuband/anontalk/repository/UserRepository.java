package org.shinaikessokuband.anontalk.repository;

import org.shinaikessokuband.anontalk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);
    List<User> findByUserId(String account);
    List<User> findByPhoneNumber(String password);
    List<User> deleteByUserId(String account);
    List<User> findByUsername(String username);
}
