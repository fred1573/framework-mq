package com.tomasky.framework.mc.controller.vo;

import java.util.Date;

/**
 * Created by  idea
 * user: yutaoxun
 * data: 2016/5/5
 * email: yutaoxun@gmail.com
 * company: fanqielaile
 * description: 消息类型服务类接口.
 */
public class MsgTypeVo {
    private String system;
    private String msgType;
    private String principal;
    private String description;
    private Date createTime;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        return "[ principal=" +principal+",system="+system+",msgType="+msgType+",description="+description+" ]";
    }

}
