package org.shinaikessokuband.anontalk.service;


import org.shinaikessokuband.anontalk.dto.MessageDto;
import org.shinaikessokuband.anontalk.dto.RoomDto;
import org.shinaikessokuband.anontalk.entity.Message;
import org.shinaikessokuband.anontalk.entity.Room;

public interface IChatService {

    Message saveMessage(MessageDto messageDto);

    Room createRoom(RoomDto roomDto);
}
