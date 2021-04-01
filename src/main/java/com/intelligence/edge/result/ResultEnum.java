package com.intelligence.edge.result;

/**
 * @description: 错误信息Enum
 * @author: uu
 * @create: 10-27
 **/
public enum ResultEnum {

    //可自行定义，与前端交互
    UNKNOWN_ERROR(-1,"未知错误"),
    SUCCESS(20000,"success"),
    FAIL(500,"fail"),
    STUDENT_NOT_EXIST(1,"用户不存在"),
    STUDENT_IS_EXISTS(2,"用户已存在"),
    DATA_IS_NULL(3,"数据为空"),
    DELETE_FAIL(5,"删除失败"),
    UPDATE_FAIL(6,"更新失败"),
    VERIFY_FAIL(7,"验证失败");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
