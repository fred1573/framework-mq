package com.tomato.mq.support.core;

/**
 * 权重枚举
 *
 * @author frd
 *         2016/6/22.
 */
public enum PushWeight {

    DEFAULT(5), MIN(1), MAX(9);

    private int weight;

    PushWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
