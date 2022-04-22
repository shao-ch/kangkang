package com.kangkang.tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.annotation.common.FieldConvert;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: ConverUtils
 * @Author: shaochunhai
 * @Date: 2021/8/17 12:29 下午
 * @Description: TODO
 */
public class ConverUtils {


    /**
     * map赚对象
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T mapToClass (Map<String,Object> map,Class<T> clazz){
        T t=null;

        try {
            t = clazz.newInstance();
            //获取所有属性
            Field[] fields = clazz.getDeclaredFields();

            //便利所有属性
            for (Field field : fields) {

                //强制执行，可以击穿私有属性
                field.setAccessible(true);
                //给属性赋值
                String type = field.getType().toString();
                if (type.contains("double")){   //double格式处理
                    field.set(t, Double.parseDouble(map.get(field.getName())==null?"0":map.get(field.getName()).toString()));
                }else if (type.contains("String")){   //String格式处理
                    field.set(t, JSONObject.toJSONString(map.get(field.getName())));
                }else if (type.contains("Date")){  //时间格式处理
                    field.set(t,map.get(field.getName())==null?new Date():new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse
                            (JSONObject.toJSONString(map.get(field.getName()))));
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return t;
    }


    /**
     * 获取随机用户名，这个是按照时间错加密来获取的用户名
     * @return
     */
    public static String getRandomUserName(){
        StringBuffer stringBuffer = null;
        try {
            long time = System.currentTimeMillis();
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] digest = md5.digest(Long.toString(time).getBytes(StandardCharsets.UTF_8));

            stringBuffer = new StringBuffer();
            String temp = null;
            for (int i=0;i<digest.length;i++){
                temp = Integer.toHexString(digest[i] & 0xFF);
                if (temp.length()==1){
                    //1得到一位的进行补0操作
                    stringBuffer.append("0");
                }
                stringBuffer.append(temp);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

        return stringBuffer.toString();
    }
    /**
     * 数组转化为json
     * @param specArgument
     * @return
     */
    private String arrayToJson(String specArgument) {
        String[] parse = (String[]) JSONObject.parse(specArgument);
        HashMap<String, Object> map = new HashMap<>();
        for (String s : parse) {
            String[] split = s.split(":");
            map.put(split[0],split[1]);
        }
        return JSONObject.toJSONString(map);
    }

    /**
     * 分页数据转化成本项目的格式
     * @param page
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> PageInfo<T> pageConver (Page<T> page){

        PageInfo<T> tPageInfo = new PageInfo<>();

        tPageInfo.setData(page.getRecords());

        tPageInfo.setSize(page.getSize());
        tPageInfo.setTotal(page.getTotal());
        return tPageInfo;

    }

    /**
     * 数据格式转化
     * @param jsonObject  json数据
     * @param tClass    转化的类型
     * @param <T>    返回的值
     * @return
     */

    public static <T> T dataConvert (JSONObject jsonObject,Class<T> clasz) throws InstantiationException, IllegalAccessException {

        //获取所有的属性，包括父类的属性
        ArrayList<Field> fieldList = new ArrayList<>();
        Object obj = clasz.newInstance();

        Class<? super T> superclass =clasz;
        while (superclass!=null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(clasz.getDeclaredFields())));
            superclass = clasz.getSuperclass();
        }
        Field[] fields=new Field[fieldList.size()];
        //数组转化
        fields = fieldList.toArray(fields);
        //做属性的注入
        for (Field field : fields) {

            field.setAccessible(true);

            String fieldName = field.getName();

            //获取属性上的是否含有FieldConvert注解
            FieldConvert fieldConvert = field.getAnnotation(FieldConvert.class);
            //判断是否是null
            if (fieldConvert==null){
                //然后根据数据类型做数据的转化
                if (field.getType()==String.class){
                    String str = jsonObject.getString(fieldName);
                    field.set(obj,str);
                }else if (field.getType()==boolean.class){
                    boolean str=  jsonObject.getBoolean(fieldName);
                    field.set(obj,str);
                }else if (field.getType()==Date.class){
                    Date str=  jsonObject.getDate(fieldName);
                    field.set(obj,str);
                }else if (field.getType()==Integer.class||field.getType()==int.class){
                    Integer str=  jsonObject.getInteger(fieldName);
                    field.set(obj,str);
                }else if (field.getType()==String[].class){  //数组转化
                    //数据转化
                    JSONArray jsonArray = jsonObject.getJSONArray(fieldName);
                    //转化成数组
                    String[] strings = jsonArray.toJavaObject(String[].class);
                    field.set(obj,strings);
                }else if (field.getType()==List.class){  //list集合
                    //数据转化
                    JSONArray jsonArray = jsonObject.getJSONArray(fieldName);
                    //转化成数组
                    List<String> s = jsonArray.toJavaList(String.class);
                    field.set(obj,s);
                }else {  //否则这里做个强转的类型，如果转化异常的情况下就抛异常
                    String str = (String) jsonObject.get(fieldName);
                    field.set(obj,str);
                }
            }else {
                //首先进行第一步的数据类型转化
                Class<?> type = field.getType();
                if (type==List.class){

                }

                String[] parmNames = fieldConvert.parmName();

                //获取注解的类型
                Class[] paramsTypes = fieldConvert.paramsType();

                //抛异常，参数和类型不匹配
                if (parmNames.length!=paramsTypes.length){
                    throw new RuntimeException("=========参数名称数量和参数类型的数量不一致==========");
                }
                //解析数据


            }
        }

        return null;

    }




}
