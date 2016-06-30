package com.tomasky.framework.mc.controller.vo;

/**
 * @author Hunhun
 */
public class ConcretePersisVO {

    private String id;
    private String msgType;
    private String persisTime;
    private Object content;

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

    public String getPersisTime() {
        return persisTime;
    }

    public void setPersisTime(String persisTime) {
        this.persisTime = persisTime;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}

