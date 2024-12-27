package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.entity.User;

public interface SettingService {

    User updateStudentByAccount(String account, String name, String email, String password, String hobbies, String phoneNumber);

    User getUserByAccount(String account);
}
