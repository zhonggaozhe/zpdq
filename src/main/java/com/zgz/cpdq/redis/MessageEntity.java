package com.zgz.cpdq.redis;

import com.zgz.cpdq.enums.MessageTypeEnums;
import lombok.Data;

import java.io.Serializable;

@Data
public class MessageEntity implements Serializable {

    private String id;

    private MessageTypeEnums messageTypeEnums;

    private Object content;

    public MessageEntity() {
        super();
    }

    public MessageEntity(String id, Object content) {
        super();
        this.id = id;
        this.content = content;
    }

    public MessageEntity(String id, MessageTypeEnums messageTypeEnums, Object content) {
        this.id = id;
        this.messageTypeEnums = messageTypeEnums;
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageEntity [id=" + id + ", content=" + content + "]";
    }
}
