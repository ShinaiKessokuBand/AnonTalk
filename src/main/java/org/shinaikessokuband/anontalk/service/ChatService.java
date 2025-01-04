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

/**
 * 聊天服务类，处理聊天相关的业务逻辑。
 */
@Service
public class ChatService implements IChatService {

    // 消息存储库，用于与消息数据交互
    private final MessageRepository messageRepository;

    // 房间存储库，用于与房间数据交互
    private final RoomRepository roomRepository;

    /**
     * 构造函数，通过依赖注入获取消息和房间存储库的实例。
     *
     * @param messageRepository 消息存储库
     * @param roomRepository    房间存储库
     */
    public ChatService(MessageRepository messageRepository, RoomRepository roomRepository) {
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * 保存消息到聊天房间。
     *
     * @param messageDto 要保存的消息数据传输对象
     * @return 保存的消息实体
     */
    @Override
    public Message saveMessage(MessageDto messageDto) {

        // 根据房间 ID 查找聊天房间，如果未找到则抛出异常
        Room room = roomRepository.findById(messageDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("聊天房间未找到"));

        // 创建新的消息实体
        Message message = new Message();
        message.setRoomId(room.getRoomId()); // 设置房间 ID
        message.setUsername(messageDto.getUsername()); // 设置用户名
        message.setMessage(messageDto.getMessage()); // 设置消息内容
        message.setTimestamp(LocalDateTime.now()); // 设置时间戳为当前时间

        // 保存消息并返回保存的消息实体
        return messageRepository.save(message);
    }

    /**
     * 创建新的聊天房间。
     *
     * @param roomDto 要创建的房间数据传输对象
     * @return 创建的房间实体
     */
    @Override
    public Room createRoom(RoomDto roomDto) {
        // 创建新的房间实体
        Room room = new Room(roomDto.getRoomName());
        // 保存房间并返回创建的房间实体
        return roomRepository.save(room);
    }
}

