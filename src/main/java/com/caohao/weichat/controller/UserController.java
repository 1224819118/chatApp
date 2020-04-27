package com.caohao.weichat.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.caohao.weichat.entity.Users;
import com.caohao.weichat.entity.bo.UsersBo;
import com.caohao.weichat.entity.vo.UsersVo;
import com.caohao.weichat.service.MyFriendsService;
import com.caohao.weichat.service.UsersService;
import com.caohao.weichat.util.MsgUtil;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    MyFriendsService myFriendsService;
    @Autowired
    UsersService usersService;

    @PostMapping("/login")
    public MsgUtil login(@RequestBody Users users){
        System.out.println(users.getCid());
        UsersVo usersVo = usersService.loginCheck(users);
        if (usersVo==null){
            return MsgUtil.REQUESTERROR("用户不存在");
        }
        return MsgUtil.ISOK(usersVo);
    }

    @PostMapping("register")
    public MsgUtil register(@RequestBody Users users){
        boolean save = usersService.save(users);
        if (save==false){
            return MsgUtil.SYSERROR("注册失败，请重试");
        }
        return MsgUtil.ISOK(UsersVo.copy(users));
    }

    @PostMapping("/uploadFaceBase64")
    public MsgUtil uploadFaceBase64(@RequestBody UsersBo userBo){
        Users users = usersService.upoadFaceImage(userBo);
        if (users == null){
            return MsgUtil.SYSERROR("更新头像失败,请重试");
        }
        return MsgUtil.ISOK(UsersVo.copy(users));
    }

    @PostMapping("setNickName")
    public MsgUtil setNickName(@RequestBody UsersBo userBo){
        String nickName = userBo.getNickName();
        UpdateWrapper<Users> updateWrapper = new UpdateWrapper<Users>();
        updateWrapper.eq("id",userBo.getUserId())
                .set("nick_name",nickName);
        boolean update = usersService.update(updateWrapper);
        if (update == false){
            return MsgUtil.SYSERROR("修改昵称失败，请重试");
        }
        Users users = usersService.getById(userBo.getUserId());
        return MsgUtil.ISOK(users);
    }

    /**
     * 搜索好友准确查找，而不是模糊查询
     * @param myUserId
     * @param friendUserName
     * @return
     */
    @PostMapping("serach")
    public MsgUtil search(String myUserId,String friendUserName){
        System.out.println(myUserId+"----"+friendUserName);
        if (myUserId==null||friendUserName==null){
            return MsgUtil.REQUESTERROR("");
        }
        MyFriendsService.SerachStatusEnum serachStatusEnum = myFriendsService.searchFriends(myUserId, friendUserName);
        if (serachStatusEnum.status==0){
            Users user = usersService.queryUserByUserName(friendUserName);
            if (user==null){
                return MsgUtil.SYSERROR("系统繁忙请稍后操作");
            }else {
                return MsgUtil.ISOK(UsersVo.copy(user));
            }
        }else {
            return MsgUtil.REQUESTERROR(serachStatusEnum.msg);
        }


    }


}
