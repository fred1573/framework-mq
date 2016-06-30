package com.tomato.mq.support.netty.model;

import com.tomato.mq.support.message.MessageType;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 *         2015-10-17 11:13
 */
public class Signal {

    private RequestType reqType;
    private String message;
    private Set<MessageType> messageTypes;

    public Signal() {
    }

    public Signal(RequestType reqType) {
        this.reqType = reqType;
    }

    public Signal(RequestType reqType, String message) {
        this.reqType = reqType;
        this.message = message;
    }

    public Signal(RequestType reqType, String message, MessageType messageType) {
        this.reqType = reqType;
        this.message = message;
        Set<MessageType> messageTypeSet = new HashSet<>();
        messageTypeSet.add(messageType);
        this.messageTypes = messageTypeSet;
    }

    public Signal(RequestType reqType, String message, Set<MessageType> messageTypes) {
        this.reqType = reqType;
        this.message = message;
        this.messageTypes = messageTypes;
    }

    public enum RequestType {
        BEAT,
        TRANSPORT,
        ACK_SUCCESS,
        ACK_FAIL,
        ADD_LISTENER,
        ACK_ADD_LISTENER_FAIL
    }

    public RequestType getReqType() {
        return reqType;
    }

    public void setReqType(RequestType reqType) {
        this.reqType = reqType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<MessageType> getMessageTypes() {
        return messageTypes;
    }

    public void setMessageTypes(Set<MessageType> messageTypes) {
        this.messageTypes = messageTypes;
    }

    @Override
    public String toString() {
        return "Signal{" +
                "reqType=" + reqType +
                ", message='" + message + '\'' +
                ", messageTypes=" + messageTypes +
                '}';
    }
}
