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


    /**
     * 创建一个新的聊天室。
     *
     * 这个方法用于创建一个新的聊天室。它接收一个包含聊天室信息的 `RoomDto` 对象，
     * 然后通过调用服务层的 `createRoom` 方法来创建一个聊天室。
     *
     * 请求的 URL：/chat/create
     * 请求方法：POST
     *
     * @param roomDto 包含聊天室创建所需信息的对象，使用了 @Validated 注解进行验证
     * @return 创建聊天室后的返回信息，表示聊天室已成功创建。
     */
    @PostMapping("/create") //URL:localhost:8080/chat/create method:post
    public String create(@Validated @RequestBody RoomDto roomDto){
        Room roomNew = chatService.createRoom(roomDto);
        return "A room was created!";
    }

    /**
     * 向指定聊天室发送消息。
     *
     * 该方法接收一个包含消息内容的 `MessageDto` 对象，并将其保存到指定的聊天室中。
     * 通过调用服务层的 `saveMessage` 方法来保存消息。
     *
     * 请求的 URL：/chat/{roomId}
     * 请求方法：POST
     *
     * @param roomId 聊天室的唯一标识符，通过 URL 路径变量传入
     * @param messageDto 包含要发送的消息内容的对象，使用了 @Validated 注解进行验证
     * @return 包含消息详情的响应，表示消息已成功发送并保存。
     */
    @PostMapping("/{roomId}") //URL:localhost:8080/chat/{roomId} method:post
    public ResponseMessage save(@PathVariable Integer roomId, @Validated @RequestBody MessageDto messageDto) {
        Message messageNew = chatService.saveMessage(messageDto);
        return ResponseMessage.success(messageNew);
    }

}
