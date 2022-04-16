package com.kangkang.tools;

import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName: KBase64Utils
 * @Author: shaochunhai
 * @Date: 2022/4/16 4:22 下午
 * @Description: TODO
 */
public class KBase64Utils {


    //base64 解码
    public static String decode(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        return new String(Base64.decodeBase64(bytes));
    }

    //base64 编码
    public static String encode(String str) {

        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        return new String(Base64.encodeBase64(bytes));
    }


    public static void main(String[] args) {

        String str="bacv";

        String encode = encode(str);
        System.out.println("encode = " + encode);

        String decode = decode(encode);
        System.out.println("decode = " + decode);
    }
}
