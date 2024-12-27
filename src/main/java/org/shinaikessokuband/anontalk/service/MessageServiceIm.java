package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageServiceIm implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void exportChatHistory(String filePath) throws IOException {
//        List<Message> messages = messageRepository.findAll(); // 获取所有消息
//        try (FileWriter writer = new FileWriter(filePath)) {
//            for (Message message : messages) {
//                writer.write(message.getSender() + "," + message.getContent() + "\n");
//            }
//        }
    }
}
