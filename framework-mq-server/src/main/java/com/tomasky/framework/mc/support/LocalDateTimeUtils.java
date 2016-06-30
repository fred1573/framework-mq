package com.tomasky.framework.mc.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Hunhun
 */
public class LocalDateTimeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateTimeUtils.class);

    private LocalDateTimeUtils() {
    }

    public static LocalDateTime getLocalDateTime(Object obj) {
        LocalDateTime dateTime;
        Date date;
        try {
            if (obj instanceof Date) {
                date = (Date) obj;
            } else if (obj instanceof String) {
                date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(obj.toString());
            }else {
                LOGGER.error("转换LocalDateTime时入参类型异常, type={}", obj.getClass().getName());
                throw new RuntimeException("转换LocalDateTime时入参类型异常, type=" + obj.getClass().getName());
            }
        } catch (Exception e) {
            String error = "转换LocalDateTime时异常";
            LOGGER.error(error);
            throw new RuntimeException(error);
        }
        dateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return dateTime;
    }
}
