package com.caohao.weichat.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.caohao.weichat.entity.FriendsRequest;
import com.caohao.weichat.entity.MyFriends;
import com.caohao.weichat.entity.Users;
import com.caohao.weichat.entity.vo.FriendRequestVo;
import com.caohao.weichat.entity.vo.MyFriendsVo;
import com.caohao.weichat.entity.vo.UsersVo;
import com.caohao.weichat.service.FriendsRequestService;
import com.caohao.weichat.service.MyFriendsService;
import com.caohao.weichat.service.UsersService;
import com.caohao.weichat.util.MsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
public class FriendsController {
    @Autowired
    MyFriendsService myFriendsService;
    @Autowired
    FriendsRequestService friendsRequestService;
    @Autowired
    UsersService usersService;

    /**
     * 申请加好友
     * @param myUserId
     * @param friendUserName
     * @return
     */
    @PostMapping("/addfriend")
    public MsgUtil addFriend(String myUserId,String friendUserName){
        if (myUserId==null||friendUserName==null){
            return MsgUtil.REQUESTERROR("");
        }
        MyFriendsService.SerachStatusEnum serachStatusEnum = myFriendsService.searchFriends(myUserId, friendUserName);
        if (serachStatusEnum.status==0){
            FriendsRequest friendsRequest = new FriendsRequest();
            friendsRequest.setSendUserId(myUserId);
            QueryWrapper<Users> wrapper = new QueryWrapper<Users>();
            wrapper.eq("user_name",friendUserName);
            Users targetUser = usersService.getOne(wrapper);
            friendsRequest.setAcceptUserId(targetUser.getId());
            friendsRequest.setRequestDateTime(new Date());
            boolean b = friendsRequestService.checkIsAlreadyRequest(myUserId, targetUser.getId());
            if (b == true){
                return MsgUtil.REQUESTERROR("已经申请过了");
            }else {
                boolean save = friendsRequestService.save(friendsRequest);
                if (save == false){
                    return MsgUtil.SYSERROR("系统繁忙请稍后再试");
                }
                return MsgUtil.ISOK("已发送申请");
            }

        }else {
            return MsgUtil.REQUESTERROR(serachStatusEnum.msg);
        }
    }

    /**
     * 返回用户的全部好友
     */

    /**
     * 返回用户所接收到的所有好友请求
     */
    @PostMapping("selectFriendRequests")
    public MsgUtil selectFriendRequestList(String myId){
        List<FriendsRequest> friendsRequests = friendsRequestService.selectFriendRequestByUserId(myId);
        List<Users> usersList = usersService.friendRequestTransfoToUsers(friendsRequests);
        List<FriendRequestVo> friendRequestVoList = friendsRequestService.usersTranfoToFriendRequestVo(usersList);
        if (friendRequestVoList.size()==0){
            return MsgUtil.REQUESTERROR("没有人加你好友");
        }
        return MsgUtil.ISOK(friendRequestVoList);
    }

    /**
     * 确定添加某人,或者忽略请求
     * 1:通过
     * 0：忽略
     */
    @PostMapping("operFriendRequest")
    public MsgUtil operFriendRequest(String myId,String sendUserId,int operType){
        System.err.println("请求为"+operType);
        if (operType==0){
            friendsRequestService.operRequest(myId, sendUserId);
            return MsgUtil.ISOK("忽略成功");
        }else if (operType==1){
            friendsRequestService.operRequest(myId,sendUserId);
            myFriendsService.passFriendRequest(myId,sendUserId);
            myFriendsService.passFriendRequest(sendUserId,myId);
            return MsgUtil.ISOK("添加成功");
        }
        return MsgUtil.REQUESTERROR("请求出现问题,请稍后重试");
    }

    /**
     * 查询好友列表
     * @param myId
     * @return
     */
    @PostMapping("myFriends")
    public MsgUtil myFriends(String myId){
        if (myId==null){
            return MsgUtil.REQUESTERROR("请求出现问题,请稍后重试");
        }
        List<MyFriendsVo> myFriendsVos = new LinkedList<MyFriendsVo>();
        try {
            List<MyFriends> myFriends = myFriendsService.selectFriendList(myId);
            List<Users> usersList = usersService.myFriendsTransfoToUsers(myFriends);
            myFriendsVos = myFriendsService.usersTranfoToMyFriendsVo(usersList);
        } catch (Exception e) {
            return MsgUtil.REQUESTERROR("请求出现问题,请稍后重试");
        }
        if (myFriendsVos==null||myFriendsVos.size()<=0){
            return MsgUtil.REQUESTERROR("请求出现问题,请稍后重试");
        }else {
            return MsgUtil.REQUESTERROR(myFriendsVos);
        }

    }


}
