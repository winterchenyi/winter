//package com.yestic.winter.component;
//
//import com.alibaba.fastjson.JSONObject;
//import com.yestic.winter.annotation.ParamValidation;
//import com.yestic.winter.constant.ResCode;
//import com.yestic.winter.dto.ParamsBody;
//import com.yestic.winter.dto.ReturnBody;
//import com.yestic.winter.exception.ParamValidationException;
//import com.yestic.winter.util.ReflectUtil;
//import com.yestic.winter.dto.ValidationResult;
//import com.yestic.winter.util.ValidationUtils;
//import com.yestic.winter.util.WinterUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.validation.groups.Default;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 定义切面参数验证方法
// * Created by chenyi on 2017/12/22
// */
//@Aspect
//@Component
//public class ParamAspect {
//
//    private static final Logger logger = LoggerFactory.getLogger(ParamAspect.class);
//
//    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) && args(paramsBody,..)")
//    public Object doAround(ProceedingJoinPoint joinPoint, ParamsBody paramsBody) throws Throwable {
//        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
//        String objClassName = methodSignature.getDeclaringType().getName();
//        Method method = methodSignature.getMethod();
//        String methodName = method.getName();
//        Object returnValue = null;
//        long starttime = System.currentTimeMillis();
//        logger.info(String.format("controller:{%s}-method:{%s},starttime=(%s)", objClassName, methodName, starttime));
//        logger.info(String.format("controller:{%s},method:{%s},params:{%s}", objClassName, methodName, paramsBody.toString()));
//        if (paramsBody != null && paramsBody.getBody()==null) {
//            return ReturnBody.build(ResCode.PARAMETERMISSING);
//        } else {
//            this.validParams(paramsBody, method);
//            returnValue = joinPoint.proceed();
//            logger.info(String.format("controller:{%s}-method:{%s},返回值:(%s)", objClassName, methodName, returnValue.toString()));
//            return returnValue;
//        }
//    }
//
//    private void validParams(ParamsBody paramsBody, Method method) throws Exception {
//        ParamValidation validation = method.getAnnotation(ParamValidation.class);
//        if (null != validation) {
//            Class bean = validation.bean();
//            if (bean == Default.class) {
//                throw new IllegalArgumentException("ParamValidation 注解  bean 属性 未设置");
//            }
//
//            String[] parmas = validation.value();
//            String[] exclude = validation.exclude();
//            Map<String, String> excludeMap = null;
//            String s;
//            if (WinterUtils.isNotNull(exclude)) {
//                excludeMap = new HashMap();
//
//                for(int i = 0; i < exclude.length; ++i) {
//                    s = exclude[i];
//                    excludeMap.put(s, s);
//                }
//            }
//
//            JSONObject jsonObject = paramsBody.getBody();
//            s = null;
//
//            Object objBean;
//            try {
//                objBean = ReflectUtil.fillBeanFromJson(jsonObject, bean);
//            } catch (Exception var15) {
//                throw new ParamValidationException(var15.getMessage());
//            }
//
//            ValidationResult validationResult;
//            int i;
//            String fieldname;
//            if (WinterUtils.isNotNull(parmas)) {
//                String[] var11 = parmas;
//                i = parmas.length;
//
//                for(int var13 = 0; var13 < i; ++var13) {
//                    fieldname = var11[var13];
//                    validationResult = ValidationUtils.validateProperty(objBean, fieldname);
//                    if (validationResult.isHasErrors()) {
//                        throw new ParamValidationException(JSONObject.toJSONString(validationResult.getErrorMsg()));
//                    }
//                }
//            } else if (WinterUtils.isNotNull(exclude)) {
//                Field[] fields = bean.getDeclaredFields();
//
//                for(i = 0; i < fields.length; ++i) {
//                    Field field = fields[i];
//                    fieldname = field.getName();
//                    if (!excludeMap.containsKey(fieldname)) {
//                        validationResult = ValidationUtils.validateProperty(objBean, fieldname);
//                        if (validationResult.isHasErrors()) {
//                            throw new ParamValidationException(JSONObject.toJSONString(validationResult.getErrorMsg()));
//                        }
//                    }
//                }
//            } else if (WinterUtils.isNull(parmas) && WinterUtils.isNull(exclude)) {
//                validationResult = ValidationUtils.validateEntity(objBean);
//                if (validationResult.isHasErrors()) {
//                    throw new ParamValidationException(JSONObject.toJSONString(validationResult.getErrorMsg()));
//                }
//            }
//        }
//
//    }
//}
