package org.shinaikessokuband.anontalk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchServiceIm implements MatchService {

    @Autowired
    private UserService userService;

    private Random random = new Random();

    @Override
    public String findMatch(String username) {
        Set<String> onlineUsers = userService.getOnlineUsers();
        List<String> availableUsers = onlineUsers.stream()
                .filter(user -> !user.equals(username)) // 排除自己
                .collect(Collectors.toList());

        if (availableUsers.isEmpty()) {
            return null; // 没有找到匹配的用户
        }
        int index = random.nextInt(availableUsers.size());
        return availableUsers.get(index); // 返回随机匹配的用户
    }
}
