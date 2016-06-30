package com.tomato.mq.support.core;

import com.tomato.mq.support.constant.Constant;
import com.tomato.mq.support.message.MessageType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hunhun
 *         14:37
 */
public class Destination implements IDestination {

    public static final Map<Enum<MessageType>, String> DEST_MAP = new HashMap<>();

    static{
        DEST_MAP.put(MessageType.BIZ_LOG, Constant.DEST_BIZ_LOG);
        DEST_MAP.put(MessageType.SYS_LOG, Constant.DEST_SYS_LOG);
        DEST_MAP.put(MessageType.SYS_EVENT, Constant.DEST_SYS_EVENT);
        DEST_MAP.put(MessageType.BIZ_MESSAGE, Constant.DEST_BIZ_MESSAGE);
    }

}
