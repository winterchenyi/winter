package com.yestic.winter.util;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by chenyi on 2017/12/22
 */
public class ReflectUtil {

    public ReflectUtil() {
    }

    public static Object fillBeanFromJson(Object jsonObject, Class clazz) throws Exception {
        Object bean = Class.forName(clazz.getName()).newInstance();
        Method[] methods = clazz.getDeclaredMethods();
        Method[] var4 = methods;
        int var5 = methods.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if (method.getName().startsWith("set")) {
                String field = method.getName();
                field = field.substring(field.indexOf("set") + 3);
                field = field.toLowerCase().charAt(0) + field.substring(1);
                Object fieldValue = ((JSONObject)jsonObject).get(field);
                if (fieldValue != null) {
                    fieldValue = ParameterTypeConverter(fieldValue.toString(), method.getParameterTypes()[0]);
                }

                method.invoke(bean, fieldValue);
            }
        }

        return bean;
    }

    public static Object bindBeanFronJson(JSONObject jsonObject, Class clazz, Map<String, String> map) throws Exception {
        Object bean = Class.forName(clazz.getName()).newInstance();
        Method[] methods = clazz.getDeclaredMethods();
        Method[] var5 = methods;
        int var6 = methods.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Method method = var5[var7];
            if (method.getName().startsWith("set")) {
                String field = method.getName();
                if (map.containsKey(field)) {
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    Object fieldValue = jsonObject.get(field);
                    if (fieldValue != null) {
                        fieldValue = ParameterTypeConverter(fieldValue.toString(), method.getParameterTypes()[0]);
                    }

                    method.invoke(bean, fieldValue);
                }
            }
        }

        return bean;
    }

    public static Object ParameterTypeConverter(String value, Class clazz) throws Exception {
        String typeName = clazz.getName();

        try {
            if (value.getClass().getName().equals(typeName)) {
                return value;
            } else if (!"Boolean".equalsIgnoreCase(typeName) && !"java.lang.Boolean".equals(typeName)) {
                if (!"int".equalsIgnoreCase(typeName) && !"Integer".equals(typeName) && !"java.lang.Integer".equals(typeName)) {
                    if (!"String".equalsIgnoreCase(typeName) && !"java.lang.String".equals(typeName)) {
                        if (!"double".equalsIgnoreCase(typeName) && !"Double".equalsIgnoreCase(typeName) && !"java.lang.Double".equals(typeName)) {
                            if (!"float".equalsIgnoreCase(typeName) && !"Float".equalsIgnoreCase(typeName) && !"java.lang.Float".equals(typeName)) {
                                throw new IllegalArgumentException("值[" + value + "]无对应数据类型");
                            } else {
                                return new Float(value);
                            }
                        } else {
                            return new Double(value);
                        }
                    } else {
                        return new String(value);
                    }
                } else {
                    return new Integer(value);
                }
            } else {
                return !value.equalsIgnoreCase("true") && !value.equals("1") ? new Boolean(false) : new Boolean(true);
            }
        } catch (Exception var4) {
            throw new IllegalArgumentException("值[" + value + "]与类型 " + clazz.getSimpleName() + "不匹配");
        }
    }
}
