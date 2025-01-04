package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.entity.User;

public interface SettingService {

    User updateStudentByAccount(
            Integer userId,

            String name,

            String email,

            String password,

            String hobbies,

            String phoneNumber
    );

    User getUserByAccount(String userName);
}
