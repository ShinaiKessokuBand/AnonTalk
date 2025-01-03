package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.entity.User;
import org.shinaikessokuband.anontalk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettingServiceIm implements SettingService{

    private final UserRepository userRepository;

    public SettingServiceIm(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User updateStudentByAccount(Integer userId, String name, String email,String password,String hobbies,String phoneNumber) {
        if (userRepository.findByUsername(name) == null) {
            throw new IllegalArgumentException("account: " + name + " does not exist");
        }
        else {
            User userInDB = (User) userRepository.findByUsername(name);

            if (name != null && !userInDB.getUsername().equals(name)) {
                userInDB.setUsername(name);
            }
            if (email != null && !userInDB.getEmail().equals(email)) {
                userInDB.setEmail(email);
            }
            if (password != null && !userInDB.getPassword().equals(password)) {
                userInDB.setPassword(password);
            }
            if (hobbies != null && !userInDB.getHobbies().equals(hobbies)) {
                userInDB.setHobbies(hobbies);
            }
            if (phoneNumber != null && !userInDB.getPhoneNumber().equals(phoneNumber)) {
                userInDB.setPhoneNumber(phoneNumber);
            }
            User user = userRepository.save(userInDB);
            return user;
        }
    }

    @Override
    public User getUserByAccount(String userName) {
        if (userRepository.findByUsername(userName) == null) {
            throw new IllegalArgumentException("account: " + userName + " does not exist");
        }
        else {
            return (User) userRepository.findByUsername(userName);
        }
    }
}
