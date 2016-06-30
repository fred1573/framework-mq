package com.tomato.mq.client.event.listener;

import com.tomato.mq.client.event.model.MsgEvent;

import java.util.EventListener;

/**
 * @author Hunhun
 *         2015-09-11-14:29
 */
public interface MsgEventListener extends EventListener {

    void onEvent(MsgEvent event);
}
