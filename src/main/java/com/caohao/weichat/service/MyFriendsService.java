package com.caohao.weichat.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caohao.weichat.dao.ChatMsgDao;
import com.caohao.weichat.dao.MyFriendsDao;
import com.caohao.weichat.dao.UsersDao;
import com.caohao.weichat.entity.ChatMsg;
import com.caohao.weichat.entity.MyFriends;
import com.caohao.weichat.entity.Users;
import com.caohao.weichat.entity.vo.FriendRequestVo;
import com.caohao.weichat.entity.vo.MyFriendsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("myFriendsService")
public class MyFriendsService extends ServiceImpl<MyFriendsDao, MyFriends> {
public  enum SerachStatusEnum{
        SUCCESS(0,"ok.."),
        USER_NOT_EXIT(1,"查无此人"),
        NOT_YOURSELF(2,"不能添加您自己哦"),
        ALREADY_FRIENDS(3,"该用户已经是您的好友");

        public final Integer status;
        public final String msg;
        SerachStatusEnum(Integer status,String msg){
            this.msg=msg;
            this.status = status;
        }

    }
    @Autowired
    MyFriendsDao myFriendsDao;
    @Autowired
    UsersDao usersDao;

    /**
     * 查询好友是否成功
     *     SUCCESS(0,"可以添加.."),
     *     USER_NOT_EXIT(1,"查无此人"),
     *     NOT_YOURSELF(2,"不能添加您自己哦"),
     *     ALREADY_FRIENDS(3,"该用户已经是您的好友");
     */
    public SerachStatusEnum searchFriends(String myUserId,String friendUserName){

        QueryWrapper<Users> queryWrapper = new QueryWrapper<Users>();
        queryWrapper.eq("user_name",friendUserName);
        Users users = usersDao.selectOne(queryWrapper);
        if (users==null){
            return SerachStatusEnum.USER_NOT_EXIT;
        }
        System.out.println(users.getId()+"----"+myUserId);
        if (users.getId().equals(myUserId)){
            return SerachStatusEnum.NOT_YOURSELF;
        }
        QueryWrapper<MyFriends> queryWrapper2 = new QueryWrapper<MyFriends>();
        queryWrapper2.eq("my_friends_user_id",friendUserName).eq("my_user_id",myUserId);
        MyFriends myFriends = myFriendsDao.selectOne(queryWrapper2);
        if (myFriends!=null){
            return SerachStatusEnum.ALREADY_FRIENDS;
        }
        return SerachStatusEnum.SUCCESS;
    }

    /**
     * 接受好友请求方法
     */
    public boolean passFriendRequest(String myId,String sendUserId){
        MyFriends myFriends = new MyFriends();
        myFriends.setMyUserId(myId);
        myFriends.setMyFriendsUserId(sendUserId);
        int insert = myFriendsDao.insert(myFriends);
        return true;
    }
    /**
     * 查询好友列表
     */

    public List<MyFriends> selectFriendList(String myId){
        QueryWrapper<MyFriends> wrapper = new QueryWrapper<MyFriends>().eq("my_user_id",myId);
        List<MyFriends> myFriends = myFriendsDao.selectList(wrapper);

        return myFriends;
    }

    /**
     * 将usersList转化为friendsVOList
     */
    public List<MyFriendsVo> usersTranfoToMyFriendsVo(List<Users> usersList){
        List<MyFriendsVo> friendRequestVoList = new LinkedList<MyFriendsVo>();
        for (Users u:usersList) {
            friendRequestVoList.add(MyFriendsVo.copy(u));
        }
        return friendRequestVoList;
    }
}
