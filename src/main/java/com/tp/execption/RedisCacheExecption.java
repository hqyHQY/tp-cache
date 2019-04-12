package com.tp.execption;

/**
 * @Auther: hequnayu
 * @Date: 2019/4/12 16:59
 * @Description: 自定义操作redis异常
 */
public class RedisCacheExecption extends RuntimeException {

    private String message;

    public RedisCacheExecption(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
