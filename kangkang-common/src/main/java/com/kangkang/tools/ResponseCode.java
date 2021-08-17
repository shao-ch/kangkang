package com.kangkang.tools;

/**
 * @ClassName: ResponseCode
 * @Author: shaochunhai
 * @Date: 2021/8/12 10:45 上午
 * @Description: TODO
 */
public class  ResponseCode<T> {

    private Integer code;

    private String message;

    private T data;

    public ResponseCode(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseCode() {
    }

    /**
     * 代表成功
     * @return
     */
    public static BulidBody ok(){
        return new BulidBody(HttpStatusCode.OK.value());
    }

    /**
     * 设置消息体
     * @param t
     * @param <T>
     * @return
     */
    public static <T>ResponseCode<T> ok(T t){
        BulidBody ok = ok();
        return ok.body(t,"success");
    }

    public static BulidBody status(Integer code){

        return new BulidBody(code);
    }

    /**
     * 返回数据
     * @param code
     * @param body
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseCode<T> message(Integer code,T body,String message){
        return new BulidBody(code).body(body,message);
    }

    /**
     * 返回数据
     * @param httpStatusCode
     * @param body
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseCode<T> message(HttpStatusCode httpStatusCode,T body,String message){
        return new BulidBody(httpStatusCode).bodyToHttpStatus(body,message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private static class BulidBody{

        private Integer httpCode;

        private HttpStatusCode httpStatusCode;

        public BulidBody(Integer httpCode) {
            this.httpCode = httpCode;
        }

        public BulidBody(HttpStatusCode httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
        }

        public Integer getHttpCode() {
            return httpCode;
        }

        public void setHttpCode(Integer httpCode) {
            this.httpCode = httpCode;
        }

        public <T> ResponseCode<T> body(T body, String success) {
            return new ResponseCode<>(httpCode,success,body);
        }


        public <T> ResponseCode<T> bodyToHttpStatus(T body, String success) {
            return new ResponseCode<>(httpStatusCode.value(),success,body);
        }
    }
}
