package com.tomasky.framework.mc.netty.model;

import com.tomato.mq.support.netty.model.Signal;

/**
 * @author Hunhun
 *         2015-12-04 10:08
 */
public class MsgSend {

    private Signal signal;

    public MsgSend(Signal signal) {
        this.signal = signal;
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }
}
