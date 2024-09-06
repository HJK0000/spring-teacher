package org.example.springv3.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GenericTest<T> {

    private Integer status;
    private String msg;
    private T body;

    public static <B> GenericTest<?> ok(B body){

        return new GenericTest<>(200, "성공", body);
    }

    public static GenericTest<?> fail(Integer status, String msg){
        return new GenericTest<>(status, msg, null);
    }

}
