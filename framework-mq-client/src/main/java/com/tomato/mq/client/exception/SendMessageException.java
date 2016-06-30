package com.tomato.mq.client.exception;

/**
 * @author Hunhun
 *         2015-09-15 14:58
 */
public class SendMessageException extends Throwable {

    private String message;

    public SendMessageException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
