package org.example.springv3.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Resp<T> {
    private Integer status;
    private String msg;
    private T body;

    public static <B> Resp<?> ok(B body){
        return new Resp<>(200, "성공", body);
    }

    public static <B> Resp<?> ok(B body, String msg){ // 오버로딩
        return new Resp<>(200, msg , body);
    }

    public static Resp<?> fail(Integer status, String msg){
        return new Resp<>(status, msg, null);
    }
}