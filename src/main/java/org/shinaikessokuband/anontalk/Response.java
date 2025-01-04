package org.shinaikessokuband.anontalk;

import lombok.Data;

/**
 * 一个泛型响应类，用于封装响应数据、
 * 成功状态和错误信息。
 *
 * @param <T> 响应数据的类型
 */
@Data
public class Response<T> {
    // 响应中包含的数据
    private T data;

    // 指示响应是否成功
    private boolean success;

    // 如果响应未成功，则包含错误信息
    private String errorMsg;

    /**
     * 创建一个新的成功的响应实例，并包含提供的数据。
     *
     * @param data 要包含在成功响应中的数据
     * @param <K> 数据的类型
     * @return 一个新的响应实例，指示成功
     */
    public static <K> Response<K> newSuccess(K data) {
        // 创建一个新的响应实例
        Response<K> response = new Response<K>();

        // 设置数据
        response.setData(data);

        // 将此响应标记为成功
        response.setSuccess(true);

        // 返回填充后的响应
        return response;
    }

    /**
     * 创建一个新的错误响应实例，并包含提供的数据。
     *
     * @param data 要包含在错误响应中的数据
     * @param <K> 数据的类型
     * @return 一个新的响应实例，指示失败
     */
    public static <K> Response<K> newError(K data) {
        // 创建一个新的响应实例
        Response<K> response = new Response<K>();

        // 设置数据
        response.setData(data);

        // 将此响应标记为不成功
        response.setSuccess(false);

        // 返回填充后的响应
        return response;
    }

    /**
     * 设置此响应的错误信息。
     *
     * @param errorMsg 要设置的错误信息
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 获取此响应的错误信息。
     *
     * @return 错误信息
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 检查此响应是否包含错误。
     *
     * @return 如果响应不成功则返回true，否则返回false
     */
    public boolean hasError() {
        return !this.success;
    }
}

