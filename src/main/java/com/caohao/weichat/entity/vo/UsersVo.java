package com.caohao.weichat.entity.vo;

import com.caohao.weichat.entity.Users;
import lombok.Data;

@Data
public class UsersVo {
    private String id;

    private String userName;

    private String faceImage;

    private String faceImageBig;

    private String nickName;

    private String qrcode;

    private String cid;

    public static UsersVo copy(Users users){
        UsersVo usersVo = new UsersVo();
        usersVo.setId(users.getId());
        usersVo.setNickName(users.getNickName());
        usersVo.setUserName(users.getUserName());
        usersVo.setCid(users.getCid());
        usersVo.setFaceImage(users.getFaceImage());
        usersVo.setFaceImageBig(users.getFaceImageBig());
        usersVo.setQrcode(users.getQrcode());
        return usersVo;
    }
}
