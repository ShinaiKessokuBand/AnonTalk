package org.shinaikessokuband.anontalk.controller;


import lombok.Getter;
import org.shinaikessokuband.anontalk.ResponseMessage;
import org.shinaikessokuband.anontalk.dto.MessageDto;
import org.shinaikessokuband.anontalk.dto.RoomDto;
import org.shinaikessokuband.anontalk.entity.Message;
import org.shinaikessokuband.anontalk.entity.Room;
import org.shinaikessokuband.anontalk.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController //接口返回对象 并转换成json文本
@RequestMapping("/api") //localhost:8080/chat/**
public class ChatController {


    @Autowired
    IChatService chatService;


    //创建聊天室
    @PostMapping("/create") //URL:localhost:8080/chat/create method:post
    public String create(@Validated @RequestBody RoomDto roomDto){
        Room roomNew = chatService.createRoom(roomDto);
        return "A room was created!";
    }

    //发送消息
    @PostMapping("/{roomId}") //URL:localhost:8080/chat/{roomId} method:post
    public ResponseMessage save(@PathVariable Integer roomId, @Validated @RequestBody MessageDto messageDto) {
        Message messageNew = chatService.saveMessage(messageDto);
        return ResponseMessage.success(messageNew);
    }

}
