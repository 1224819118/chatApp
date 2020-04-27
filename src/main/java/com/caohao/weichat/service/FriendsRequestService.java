package com.caohao.weichat.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caohao.weichat.dao.ChatMsgDao;
import com.caohao.weichat.dao.FriendsRequestDao;
import com.caohao.weichat.entity.ChatMsg;
import com.caohao.weichat.entity.FriendsRequest;
import com.caohao.weichat.entity.Users;
import com.caohao.weichat.entity.vo.FriendRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service("friendsRequestService")
public class FriendsRequestService extends ServiceImpl<FriendsRequestDao, FriendsRequest> {
    @Autowired
    FriendsRequestDao friendsRequestDao;

    /**
     * 查看是否重复申请
     */
    public boolean checkIsAlreadyRequest(String sendId,String acceptId){
        QueryWrapper<FriendsRequest> queryWrapper = new QueryWrapper<FriendsRequest>();
        queryWrapper.eq("send_user_id",sendId).eq("accept_user_id",acceptId);
        FriendsRequest friendsRequest = friendsRequestDao.selectOne(queryWrapper);
        if (friendsRequest==null){
            return false;
        }
        return true;
    }
    /**
     * 查询一个用户接收到的好友请求
     */
    public List<FriendsRequest> selectFriendRequestByUserId(String userId){
        QueryWrapper<FriendsRequest> wrapper = new QueryWrapper<FriendsRequest>();
        wrapper.eq("accept_user_id",userId);
        List<FriendsRequest> friendsRequests = friendsRequestDao.selectList(wrapper);
        return friendsRequests;
    }

    /**
     * 将usersList转化为friendRequestVOList
     */
    public List<FriendRequestVo> usersTranfoToFriendRequestVo(List<Users> usersList){
        List<FriendRequestVo> friendRequestVoList = new LinkedList<FriendRequestVo>();
        for (Users u:usersList) {
            friendRequestVoList.add(FriendRequestVo.copy(u));
        }
        return friendRequestVoList;
    }

    /**
     * 处理请求方法
     * 忽略请求
     * @return
     */
    public boolean operRequest(String myId,String sendUserId){
        QueryWrapper<FriendsRequest> wrapper = new QueryWrapper<FriendsRequest>();
        wrapper.eq("send_user_id",sendUserId).eq("accept_user_id",myId);
        int delete = friendsRequestDao.delete(wrapper);
        if (delete>0){
            return true;
        }
        return false;
    }
}
