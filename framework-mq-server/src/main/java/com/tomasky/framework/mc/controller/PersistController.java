package com.tomasky.framework.mc.controller;

import com.tomasky.framework.mc.controller.vo.ConcretePersisVO;
import com.tomasky.framework.mc.controller.vo.SimplePersisVO;
import com.tomasky.framework.mc.service.PersisService;
import com.tomasky.framework.mc.support.HttpStatus;
import com.tomasky.framework.mc.support.LocalDateTimeUtils;
import com.tomasky.framework.mc.support.bo.ConcretePersis;
import com.tomasky.framework.mc.support.bo.Page;
import com.tomasky.framework.mc.support.bo.SimplePersis;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hunhun
 */
@Controller
@RequestMapping("/data/persist")
public class PersistController {

    @Autowired
    private PersisService persisService;

    @RequestMapping(value = "/query")
    @ResponseBody
    public Map list(PersisQueryForm queryForm, Page<SimplePersis> page) {
        page = persisService.load(convert(queryForm), page);
        Map<String, Object> result = new HashMap<>();
        result.put("status", HttpStatus.SUCCESS);
        result.put("data", convert(page.getResult()));
        result.put("pageIndex", page.getPageIndex());
        result.put("pageSize", page.getPageSize());
        result.put("totalCount", page.getTotalCount());
        return result;
    }

    private List<SimplePersisVO> convert(List<SimplePersis> source){
        List<SimplePersisVO> dest = new ArrayList<>();
        for (SimplePersis simplePersis : source) {
            SimplePersisVO vo = new SimplePersisVO();
            BeanUtils.copyProperties(simplePersis, vo, "persisTime");
            vo.setPersisTime(simplePersis.getPersisTime().toString());
            dest.add(vo);
        }
        return dest;
    }

    private Map<String, Object> convert(PersisQueryForm queryForm){
        Map<String, Object> params = new HashMap<>();
        String persisFrom = queryForm.getPersisFrom();
        String persisTo = queryForm.getPersisTo();
        String key = queryForm.getKey();
        String value = queryForm.getValue();
        if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)){
            params.put(key, value);
        }
        if(StringUtils.isNotBlank(persisFrom)){
            LocalDateTime localFrom = LocalDateTimeUtils.getLocalDateTime(persisFrom);
            params.put("persisFrom", localFrom);
        }
        if(StringUtils.isNotBlank(persisTo)){
            LocalDateTime localTo = LocalDateTimeUtils.getLocalDateTime(persisTo);
            params.put("persisTo", localTo);
        }
        return params;
    }

    @RequestMapping(value = "/page_init")
    @ResponseBody
    public Map<String, Object> pageInit(PersisQueryForm queryForm){
        Long totalCount = persisService.totalCount(convert(queryForm));
        Map<String, Object> result = new HashMap<>();
        result.put("status", HttpStatus.SUCCESS);
        Page page = new Page();
        page.setTotalCount(totalCount);
        result.put("page", page);
        return result;
    }

    @RequestMapping(value = "/detail")
    @ResponseBody
    public Map<String, Object> detail(@RequestParam("id")String id){
        Map<String, Object> result = new HashMap<>();
        ConcretePersis cp = persisService.findById(id);
        result.put("status", HttpStatus.SUCCESS);
        result.put("data", convert(cp));
        return result;
    }

    private ConcretePersisVO convert(ConcretePersis concretePersis){
        ConcretePersisVO vo = new ConcretePersisVO();
        if(concretePersis == null){
            return vo;
        }
        SimplePersis simplePersis = concretePersis.getSimplePersis();
        vo.setId(simplePersis.getId());
        vo.setMsgType(simplePersis.getMsgType());
        vo.setPersisTime(simplePersis.getPersisTime().toString());
        vo.setContent(concretePersis.getContent());
        return vo;
    }

}
