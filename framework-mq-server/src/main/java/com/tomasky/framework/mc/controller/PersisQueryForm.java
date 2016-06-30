package com.tomasky.framework.mc.controller;

/**
 * @author Hunhun
 */
public class PersisQueryForm {

    private String persisFrom;
    private String persisTo;
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPersisFrom() {
        return persisFrom;
    }

    public void setPersisFrom(String persisFrom) {
        this.persisFrom = persisFrom;
    }

    public String getPersisTo() {
        return persisTo;
    }

    public void setPersisTo(String persisTo) {
        this.persisTo = persisTo;
    }
}
