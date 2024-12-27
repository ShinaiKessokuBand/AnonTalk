# 2024/12/28 4:29 upd by zxh

### *我是按照匹配成功后会创建一个聊天室写的，并有自己写把消息存到对应聊天室的功能*

---
- 全盘删除了之前非spring boot框架下我写的东西
- controller加了我写的ChatController
- repository加了RoomRepository
- 原本在MessageDAO中的东西迁移到了MessageRepository中
- Entity中生成了getter and setter
- 新增了RoomDto与MessageDto
- service包中加入了我写的ChatService,可以实现创建聊天室功能,存储消息功能
- Message类没敢修改导致ChatService中有setter是不存在的
- 修改了application.properties 使其可以连接到服务器的数据库
- 添加了个ResponseMessage，与原先Response功能类似
- 添加了exception包，~~几乎可以忽略~~
---
### *ChatController中的方法已通过apipost测试，暂时没发现问题*
### *有一切问题可随时联系我修改，力所能及范围内可帮助写其他活*
