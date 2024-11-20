package Message;

import java.time.LocalDateTime;

public class Message {
    private int mid;
    private int rid;
    private int uid;
    private String msg;
    private LocalDateTime sent_at;//消息发出的时间

    public int getRid(int rid) {
        return rid;
    }

    public int getUid(int uid) {
        return uid;
    }

    public String getMsg() {
        return msg;
    }
}
