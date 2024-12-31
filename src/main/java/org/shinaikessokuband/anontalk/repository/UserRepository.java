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

    List<User> findByEmailAndPassword(String email, String password);
    List<User> findByUsernameAndPassword(String username, String password);
    List<User> findByPhoneNumberAndPassword(String phoneNumber, String password);
    List<User> findByUsernameAndPasswordAndPhoneNumber(String username, String password, String phoneNumber);
    List<User> findByEmailAndPasswordAndPhoneNumber(String email, String password, String phoneNumber);
}
