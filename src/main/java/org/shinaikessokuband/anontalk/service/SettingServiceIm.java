package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.entity.User;
import org.shinaikessokuband.anontalk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettingServiceIm implements SettingService{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User updateStudentByAccount(String account, String name, String email,String password,String hobbies,String phoneNumber) {
        if (userRepository.findByAccount(account) == null) {
            throw new IllegalArgumentException("account: " + account + " does not exist");
        }
        else {
            User userInDB = (User) userRepository.findByAccount(account);

            if (name != null && !userInDB.getName().equals(name)) {
                userInDB.setName(name);
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
    public User getUserByAccount(String account) {
        if (userRepository.findByAccount(account) == null) {
            throw new IllegalArgumentException("account: " + account + " does not exist");
        }
        else {
            return (User) userRepository.findByAccount(account);
        }
    }
}
