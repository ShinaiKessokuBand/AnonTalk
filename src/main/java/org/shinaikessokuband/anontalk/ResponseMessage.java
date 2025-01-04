package org.shinaikessokuband.anontalk;

import org.springframework.http.HttpStatus;

/**
 * 通用响应消息类，用于封装响应的状态码、消息和数据。
 *
 * @param <T> 响应数据的类型
 */
public class ResponseMessage<T>
{
    // 响应的状态码
    private Integer code;

    // 响应的消息
    private String message;

    // 响应中包含的数据
    private T data;

    /**
     * 构造函数，用于创建一个新的响应消息实例。
     *
     * @param code 响应的状态码
     * @param message 响应的消息
     * @param data 响应中包含的数据
     */
    public ResponseMessage(Integer code, String message, T data)
    {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 创建一个成功的响应消息实例，并包含提供的数据。
     *
     * @param data 要包含在成功响应中的数据
     * @param <T> 数据的类型
     * @return 一个新的成功的响应消息实例
     */
    public static <T> ResponseMessage<T> success(T data)
    {
        return new ResponseMessage<>(HttpStatus.OK.value(), "success", data);
    }

    /**
     * 创建一个成功的响应消息实例，不包含数据。
     *
     * @param <T> 数据的类型
     * @return 一个新的成功的响应消息实例
     */
    public static <T> ResponseMessage<T> success()
    {
        return new ResponseMessage<>(HttpStatus.OK.value(), "success", null);
    }

    /**
     * 获取响应的状态码。
     *
     * @return 状态码
     */
    public Integer getCode()
    {
        return code;
    }

    /**
     * 设置响应的状态码。
     *
     * @param code 响应的状态码
     */
    public void setCode(Integer code)
    {
        this.code = code;
    }

    /**
     * 获取响应的消息。
     *
     * @return 消息
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * 设置响应的消息。
     *
     * @param message 响应的消息
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * 获取响应中包含的数据。
     *
     * @return 响应数据
     */
    public T getData()
    {
        return data;
    }

    /**
     * 设置响应中包含的数据。
     *
     * @param data 响应数据
     */
    public void setData(T data)
    {
        this.data = data;
    }

    /**
     * 检查响应是否包含数据。
     *
     * @return 如果包含数据则返回true，否则返回false
     */
    public boolean hasData()
    {
        return this.data != null;
    }

    /**
     * 检查响应是否成功。
     *
     * @return 如果状态码为200则返回true，否则返回false
     */
    public boolean isSuccess()
    {
        return this.code.equals(HttpStatus.OK.value());
    }
}

