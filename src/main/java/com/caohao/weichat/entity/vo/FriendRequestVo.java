package com.caohao.weichat.entity.vo;

import com.caohao.weichat.entity.Users;
import lombok.Data;

@Data
public class FriendRequestVo {
    private String sendUserId;

    private String sendUserName;

    private String sendFaceImage;

    private String sendNickName;

    public static FriendRequestVo copy(Users users){
        FriendRequestVo usersVo = new FriendRequestVo();
        usersVo.setSendUserId(users.getId());
        usersVo.setSendNickName(users.getNickName());
        usersVo.setSendUserName(users.getUserName());
        usersVo.setSendFaceImage(users.getFaceImage());
        return usersVo;
    }
}
