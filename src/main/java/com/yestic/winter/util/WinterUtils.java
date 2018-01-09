package com.yestic.winter.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 本项目需要的一些工具类
 * Created by chenyi on 2017/12/19
 */
public class WinterUtils {

    public static boolean isNull(Object obj) {
        if (obj instanceof Collection) {
            Collection collection = (Collection)obj;
            if (collection == null || collection.size() == 0) {
                return true;
            }
        } else if (obj instanceof Map) {
            Map map = (Map)obj;
            if (map == null || map.isEmpty() || map.size() == 0) {
                return true;
            }
        } else if (obj instanceof Enumeration) {
            Enumeration enumeration = (Enumeration)obj;
            if (enumeration == null || !enumeration.hasMoreElements()) {
                return true;
            }
        } else if (obj instanceof Iterator) {
            Iterator iterator = (Iterator)obj;
            if (iterator == null || !iterator.hasNext()) {
                return true;
            }
        } else if (obj instanceof Object[]) {
            Object[] objs = (Object[])((Object[])obj);
            if (objs == null || objs.length == 0) {
                return true;
            }
        } else if (obj == null) {
            return true;
        }

        return false;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static String dateToStr(Date date, String style) {
        if (date == null) {
            return "";
        } else {
            if (style == null || "".equals(style)) {
                style = "yyyy-MM-dd HH:mm";
            }

            String temp = null;
            SimpleDateFormat sdf = new SimpleDateFormat(style);
            temp = sdf.format(date);
            return temp == null ? "" : temp;
        }
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

}
