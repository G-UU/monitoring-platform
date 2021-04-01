package com.intelligence.edge.result;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bytedeco.javacv.FrameRecorder;

/**
 * @description: 封装固定格式API
 * @author: uu
 * @create: 11-26
 **/
public class StandardResult implements Serializable {


    private static final ObjectMapper MAPPER = new ObjectMapper();
    private Integer code;
    private Object data;
    private String msg;

    public StandardResult(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public StandardResult(Object data){
        this.code = 20000;
        this.data = data;
        this.msg = "success";
    }


    public static StandardResult success(Object data) {
        return  new StandardResult(data);
    }

    public static StandardResult success() {
        return new StandardResult(null);
    }





    // 无参
    public StandardResult() {

    }

    public static StandardResult build(Integer code, Object data, String msg){
        return new StandardResult(code, data, msg);
    }

    //封装消息为null的数据
    public static StandardResult build(Integer code, String msg){
        return new StandardResult(code, null, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //将json结果集转化为StandardResult对象, 有object对象
    public static StandardResult formatToPojo(String jsonData, Class<?> clazz){
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, StandardResult.class);
                }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                 if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                    } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                    }
               }
                 return build(jsonNode.get("code").intValue(), obj, jsonNode.get("msg").asText());
            } catch (Exception e) {
                return null;
            }
    }

    //没有object对象
    public static StandardResult format(String json) {
        try{
            return MAPPER.readValue(json, StandardResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }

    public static StandardResult formatToList(String jsonData, Class<?> clazz){
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0 ){
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return  build(jsonNode.get("status").intValue(), obj, jsonNode.get("msg").asText());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
