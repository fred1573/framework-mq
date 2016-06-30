package com.tomasky.framework.mc.support.bo;

import java.time.LocalDateTime;

/**
 * @author Hunhun
 */
public class SimplePersis {

    private String id;
    private String msgType;
    private LocalDateTime persisTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public LocalDateTime getPersisTime() {
        return persisTime;
    }

    public void setPersisTime(LocalDateTime persisTime) {
        this.persisTime = persisTime;
    }

}
