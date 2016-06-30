package com.tomasky.framework.mc.controller;

import com.tomasky.framework.mc.controller.vo.MsgTypeVo;
import com.tomasky.framework.mc.service.MsgTypeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by  idea
 * user: yutaoxun
 * data: 2016/5/5
 * email: yutaoxun@gmail.com
 * company: fanqielaile
 * description: 消息类型服务类接口.
 */
@Controller
@RequestMapping("/msgType")
public class MsgTypeController {

    private static final Logger LOG = LoggerFactory.getLogger(MsgTypeController.class);

    @Autowired
    private MsgTypeService msgTypeService;

    /**
     * 删除之前输入有误的信息
     */
    @RequestMapping(value = "/deleteMsgType")
    @ResponseBody
    public Map<String,String> delete(@RequestParam("msgType") String msgType) {
        Map<String,String> message = new HashMap<>();
        if(StringUtils.isEmpty(msgType)){
            message.put("message","fail");
            return message;
        }
        msgTypeService.delete(msgType);
        message.put("message","success");
        return message;
    }

    /**
     * 新增消息类型.
     */
    @RequestMapping(value = "/createMsgType")
    @ResponseBody
    public Map<String,String> create(MsgTypeVo mtv) {
        LOG.info("msgTypeVo: {}",mtv.toString());
        Map<String,String> message = new HashMap<>();
        if(StringUtils.isEmpty(mtv.getMsgType()) || StringUtils.isEmpty(mtv.getDescription())){
            message.put("message","fail");
            return message;
        }
        boolean saveResult = msgTypeService.save(mtv);
        if(saveResult){
            message.put("message","success");
        }else{
            message.put("message","fail");
        }
        return message;
    }

    /**
     * 获取指定消息类型的详细描述信息.
     */
    @RequestMapping(value = "/queryMsgType")
    @ResponseBody
    public Map queryMsgType(@RequestParam("msgType") String msgType) {
        if(StringUtils.isEmpty(msgType)){
            Map result = new HashMap();
            result.put("message","fail");
            return result;
        }
        return msgTypeService.findOne(msgType);
    }

    /**
     * 获取所有的消息类型信息.
     */
    @RequestMapping(value = "/queryMsgTypeAll")
    @ResponseBody
    public Map queryAllMsgTypies() {
        Map allData = new HashMap();
        List<Map> result = msgTypeService.findAll();
        allData.put("data",result);
        return allData;
    }
}
