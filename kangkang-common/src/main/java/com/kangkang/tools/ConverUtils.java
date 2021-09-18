package com.kangkang.tools;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
}
