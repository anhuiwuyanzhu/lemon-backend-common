package com.lemon.violet.aop;

import com.lemon.violet.annotation.Log;
import com.lemon.violet.annotation.LogField;
import com.lemon.violet.annotation.LogRef;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

@Slf4j
@Aspect
public class LogHandler {
    @Pointcut("execution(* com.lemon.violet.controller..*.*(..))")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        //日志
        StringBuffer sb = new StringBuffer();

        //ip相关日志
        // 获取请求信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String host = request.getRemoteAddr();
        sb.append("【客户端地址："+host);

        //方法类相关日志
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String name = signature.getName();
        String cla = signature.getDeclaringTypeName();
        sb.append(" 类:" + cla + " 方法:" + name + " ");


        Object[] args = pjp.getArgs();


        //出入参日志构建,执行业务
        Method method = signature.getMethod();
        logReqAdd(sb, args, method);
        Object ret = pjp.proceed();
        logRepAdd(sb, ret);


        //耗时统计
        long endTime = System.currentTimeMillis();
        long timeConsuming = endTime - startTime;
        sb.append("耗时:" + timeConsuming + "毫秒" + "】");
        log.info(sb.toString());
        return ret;
    }

    /**
     * 出参日志构建
     *
     * @param sb
     * @param ret
     */
    private void logRepAdd(StringBuffer sb, Object ret) {
//        sb.append("出参:{");
        Class<?> clz = ret.getClass();
        Log anno = clz.getAnnotation(Log.class);
        logObjAdd(sb, ret, clz, anno, false);
//        sb.append("}");
    }

    /**
     * 入参日志构建
     * @param sb
     * @param args
     * @param method
     */
    private void logReqAdd(StringBuffer sb, Object[] args, Method method) {
        Parameter[] parameters = method.getParameters();
        sb.append("入参:{");
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            String filedName = parameter.getName();
            Annotation[] annotations = parameter.getAnnotations();
            Optional<Annotation> optAnno = Arrays.stream(annotations)
                    .filter(annotation -> annotation instanceof LogField)
                    .findFirst();

            //如果有LogField注解
            if (optAnno.isPresent()) {
                LogField anno = (LogField) optAnno.get();
                reqFieldHandle(sb, args[i], filedName, anno);
            } else {
                /// TODO: 2022/9/17 如果没有 如果有LogField注解，去处理其它注解。现在只处理Log注解。后续可以扩展。这一部分逻辑需要修改
                reqLogHandle(sb, args[i], parameter);
            }
        }
        sb.append("} ");
    }

    /**
     * 请求入参处理LogField标注的字段
     * @param sb
     * @param arg
     * @param parameter
     */
    private void reqLogHandle(StringBuffer sb, Object arg, Parameter parameter) {
        Object obj = arg;
        Class<?> paramType = parameter.getType();
        Log logAnno = paramType.getAnnotation(Log.class);
        logObjAdd(sb, obj, paramType, logAnno, false);
    }

    /**
     * 请求入参处理没有被LogField注解标注的字段
     * 现在只处理Log标注的字段
     * 后续可以扩展
     * @param sb
     * @param arg
     * @param filedName
     * @param anno
     */
    private void reqFieldHandle(StringBuffer sb, Object arg, String filedName, LogField anno) {

        String msg = anno.value();
        if(arg instanceof Collection && arg != null){
            //如果是集合类型
            colFieldHandle(sb, filedName, (Collection<?>) arg, msg);
        }else if(arg !=null && arg.getClass().isArray()){
            //如果是数组类型
            arrayFieldHandle(sb, filedName, arg, msg);
        }else{
            //既不是数组类型，也不是集合类型
            normalFeildHandle(sb, arg, msg, filedName);
        }
    }

    /**
     * 如果是Log注解的类,处理其属性
     * 属性分类：普通字段、数组、集合
     * 数组注解：LogField、LogRef
     * LogField注解的字段当做普通字段处理例如：int[]
     * LogRef注解的字段当做类引用处理例如：Node[]
     *
     * @param sb         日志对象
     * @param obj        对象值
     * @param clz        对象的class
     * @param logAnno    从对象上获取哦Log注解，如果没有传递进来，如果没有Log注解为null，方法就不会执行了
     * @param isArrOrCol 是否是数组或者集合中的子元素，如果是日志就需要使用[]包裹，如果不是就使用{}包裹对象
     */
    private void logObjAdd(StringBuffer sb, Object obj, Class<?> clz, Log logAnno, boolean isArrOrCol) {
        //如果是Log注解的类,利用反射获取值,如果不是就直接结束
        if (logAnno == null) { return; }
        
        
        //对普通字段、数组或集合日志分类处理
        Field[] declaredFields = clz.getDeclaredFields();
        if (!isArrOrCol) {
            //普通对象的日志展示方式
            String claCate = logAnno.value();
            sb.append(claCate)
                    .append(":");
        }
        sb.append("{");

        
        
        //遍历每个字段进行处理
        Arrays.stream(declaredFields)
                .forEach(field -> {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Class<?> fieldClz = field.getType();
                    try {
                        Annotation logFieldAnno = field.getAnnotation(LogField.class);
                        Annotation logRefAnno = field.getAnnotation(LogRef.class);
                        Object val = field.get(obj);

                        //LogField注解的字段处理
                        logFieldHandle(sb, fieldName, fieldClz, logFieldAnno, val);

                        //LogRef注解的字段处理
                        logRefHandle(sb, fieldName, fieldClz, logRefAnno, val);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        sb.append("}");
    }

    private void logFieldHandle(StringBuffer sb, String fieldName, Class<?> fieldClz, Annotation logFieldAnno, Object val) {
        if (logFieldAnno != null && val != null) {
            //获取自定义注解的值
            String msg = ((LogField) logFieldAnno).value();
            boolean isArray = fieldClz.isArray();
            if (isArray) {
                //如果是数组类型
                arrayFieldHandle(sb, fieldName, val, msg);
            } else if (val instanceof Collection) {
                //如果是集合类型
                colFieldHandle(sb, fieldName, (Collection<?>) val, msg);
            } else {
                //既不是数组类型，也不是集合类型
                normalFeildHandle(sb, val, msg,  fieldName);
            }
        }
    }

    /**
     * 字段是数组类型field处理
     * @param sb
     * @param fileName
     * @param val
     * @param msg
     */
    private void arrayFieldHandle(StringBuffer sb, String fileName, Object val, String msg) {
        int len;
        len = Array.getLength(val);
        sb.append(StringUtils.hasLength(msg) ? msg : fileName)
                .append(":")
                .append("[");
        for (int i = 0; i < len; i++) {
            Object ele = Array.get(val, i);
            sb.append(ele);
            if (i != len - 1) {
                sb.append(",");
            }
        }
        sb.append("] ");
    }

    /**
     * 字段是集合字段field处理
     * @param sb
     * @param fieldName
     * @param val
     * @param msg
     */
    private void colFieldHandle(StringBuffer sb, String fieldName, Collection<?> val, String msg) {
        sb.append(StringUtils.hasLength(msg) ? msg : fieldName)
                .append(":")
                .append(" [");
        val.stream().forEach(ele -> {
            sb.append(ele)
                    .append(",");
        });
        sb.delete(sb.length() - 1, sb.length());
        sb.append("] ");
    }

    /**
     * 字段是正常field处理
     * @param sb
     * @param val
     * @param msg
     * @param name
     */
    private void normalFeildHandle(StringBuffer sb, Object val, String msg, String name) {
        sb.append(msg == null ? name : msg)
                .append(":")
                .append(val)
                .append(" ");
    }

    /**
     * 字段是ref类型处理
     * 需要分类处理：普通、数组、集合
     * @param sb
     * @param fieldName
     * @param fieldClz
     * @param logRefAnno
     * @param val
     */
    private void logRefHandle(StringBuffer sb, String fieldName, Class<?> fieldClz, Annotation logRefAnno, Object val) {
        int len;
        if (logRefAnno != null && val != null) {
            Class<?> refObjClz = val.getClass();
            boolean isArray = fieldClz.isArray();
            String msg = ((LogRef) logRefAnno).value();
            if (val instanceof Collection) {
                //字段是集合ref类型
                colRefHandle(sb, fieldName, (Collection<?>) val, msg);
            } else if (isArray) {
                //字段是数组ref类型
                arrayRefHandle(sb, fieldName, val, msg);
            } else {
                //字段是正常ref类型
                normalRefHandle(sb, val, refObjClz, false);
            }
        }
    }

    /**
     * 字段是集合类型ref处理
     * @param sb
     * @param fieldName
     * @param val
     * @param msg
     */
    private void colRefHandle(StringBuffer sb, String fieldName, Collection<?> val, String msg) {
        sb.append(StringUtils.hasLength(msg) ? msg : fieldName)
                .append(":")
                .append("[");
        val.forEach(ele -> {
            Class<?> clzEle = ele.getClass();
            Log refObjAnno = clzEle.getAnnotation(Log.class);
            logObjAdd(sb, ele, clzEle, refObjAnno, true);
        });
        sb.append("]");
    }

    /**
     * 字段是正常类型ref处理
     * @param sb
     * @param val
     * @param refObjClz
     * @param b
     */
    private void normalRefHandle(StringBuffer sb, Object val, Class<?> refObjClz, boolean b) {
        Log refObjAnno = refObjClz.getAnnotation(Log.class);
        logObjAdd(sb, val, refObjClz, refObjAnno, b);
    }

    /**
     * 字段是数组类型ref处理
     * @param sb
     * @param fieldName
     * @param val
     * @param msg
     */
    private void arrayRefHandle(StringBuffer sb, String fieldName, Object val, String msg) {
        int len;
        len = Array.getLength(val);
        sb.append(StringUtils.hasLength(msg) ? msg : fieldName)
                .append(":")
                .append("[");
        for (int i = 0; i < len; i++) {
            Object ele = Array.get(val, i);
            Class<?> clzEle = ele.getClass();
            Log refObjAnno = clzEle.getAnnotation(Log.class);
            logObjAdd(sb, ele, clzEle, refObjAnno, true);
        }
        sb.append("]");
    }
}
