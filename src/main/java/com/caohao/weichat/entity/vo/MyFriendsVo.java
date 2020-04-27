package com.caohao.weichat.entity.vo;

import com.caohao.weichat.entity.Users;
import lombok.Data;

@Data
public class MyFriendsVo {
    private String userId;

    private String userName;

    private String faceImage;

    private String nickName;

    public static MyFriendsVo copy(Users users){
        MyFriendsVo usersVo = new MyFriendsVo();
        usersVo.setUserId(users.getId());
        usersVo.setNickName(users.getNickName());
        usersVo.setUserName(users.getUserName());
        usersVo.setFaceImage(users.getFaceImage());
        return usersVo;
    }
}
