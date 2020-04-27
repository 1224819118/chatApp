package com.caohao.weichat.util;

import lombok.Data;

/**
 * 用于为传输的数据包装头部的工具类
 * 200：成功
 *400：请求错误
 * 500：系统内部的错误
 */
@Data
public class MsgUtil {
    private int status;
    private Object message;

    public MsgUtil(int status,Object messgae){
        this.status=status;
        this.message=messgae;
    }

    public static MsgUtil ISOK(Object messgae){
        return new MsgUtil(200,messgae);
    }

    public static MsgUtil REQUESTERROR(Object messgae){
        return new MsgUtil(400,messgae);
    }

    public static MsgUtil SYSERROR(Object messgae){
        return new MsgUtil(500,messgae);
    }


}
