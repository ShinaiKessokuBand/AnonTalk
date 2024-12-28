package org.shinaikessokuband.anontalk.service;


import org.shinaikessokuband.anontalk.dto.MessageDto;
import org.shinaikessokuband.anontalk.dto.RoomDto;
import org.shinaikessokuband.anontalk.entity.Message;
import org.shinaikessokuband.anontalk.entity.Room;
import org.shinaikessokuband.anontalk.repository.MessageRepository;
import org.shinaikessokuband.anontalk.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatService implements IChatService{

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Message saveMessage(MessageDto messageDto){

        Room room = roomRepository.findById(messageDto.getRoomId()).orElseThrow(()->new RuntimeException("chatroom not found"));

        Message message = new Message();
        message.setRoomId(room.getRoomId());
        message.setUsername(messageDto.getUsername());
        message.setMessage(messageDto.getMessage());
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

    @Override
    public Room createRoom(RoomDto roomDto){
        Room room = new Room(roomDto.getRoomName());
        return roomRepository.save(room);
    }

}
