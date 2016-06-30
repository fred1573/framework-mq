package com.tomato.mq.support.core;

import com.tomato.mq.support.message.MessageType;

import java.util.Date;

/**
 * @author Administrator
 *         2015-10-17 17:32
 */
public class SysMessage extends AbstractMessage {

    private String projectName;
    private String bizType;

    public SysMessage() {
    }

    public SysMessage(String projectName, String bizType, String content) {
        setProjectName(projectName);
        setBizType(bizType);
        setContent(content);
        setMessageType(MessageType.SYS_EVENT);
        setDate(new Date());
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

}
