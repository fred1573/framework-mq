package com.tomasky.framework.mc.service;

import com.tomasky.framework.mc.support.bo.ConcretePersis;
import com.tomasky.framework.mc.support.bo.Page;
import com.tomasky.framework.mc.support.bo.SimplePersis;

import java.util.Map;

/**
 * @author Hunhun
 *         2015-12-10 19:11
 */
public interface PersisService {

    /**
     * 按条件查询
     * @param params
     * @param page
     * @return
     */
    Page<SimplePersis> load(Map<String, Object> params, Page<SimplePersis> page);

    /**
     * 查询总数
     * @param params
     * @return
     */
    Long totalCount(Map<String, Object> params);

    /**
     * 详情
     * @param id
     * @return
     */
    ConcretePersis findById(String id);

}
