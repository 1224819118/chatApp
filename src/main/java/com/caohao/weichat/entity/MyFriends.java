package com.caohao.weichat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * (MyFriends)表实体类
 *
 * @author makejava
 * @since 2020-04-23 16:05:36
 */
@SuppressWarnings("serial")
@TableName("my_friends")
public class MyFriends extends Model<MyFriends> {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    
    private String myUserId;
    
    private String myFriendsUserId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    public String getMyFriendsUserId() {
        return myFriendsUserId;
    }

    public void setMyFriendsUserId(String myFriendsUserId) {
        this.myFriendsUserId = myFriendsUserId;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    }