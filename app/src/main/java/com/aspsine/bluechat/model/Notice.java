package com.aspsine.bluechat.model;

/**
 * Created by sf on 2015/2/2.
 */
public class Notice {

    /**
     * 接受到的消息
     */
    public static final int TYPE_IN_COMING = 0;
    /**
     * 发送的消息
     */
    public static final int TYPE_RETURNING = 1;

    /**
     * 新闻
     */
    public static final int TYPE_NEWS = 2;

    /**
     * 系统消息
     */
    public static final int TYPE_SYSTEM = 3;

    /**
     * 时间
     */
    public static final int TYPE_TIME = 4;

    public int type = -1;

    public String message;

    public Notice(int type, String message){
        this.type = type;
        this.message = message;
    }

}
