package org.shinaikessokuband.anontalk;

import lombok.Data;

@Data
public class Response<T> {
    private T data;
    private boolean success;
    private String errorMsg;

    public static <K> Response<K> newSuccess (K data) {
        Response<K> response = new Response<K>();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }
    public static <K> Response<K> newError (String errorMsg) {
        Response<K> response = new Response<K>();
        response.setSuccess(false);
        response.setErrorMsg(errorMsg);
        return response;
    }
}
