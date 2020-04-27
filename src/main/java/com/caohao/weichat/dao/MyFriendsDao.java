package com.caohao.weichat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caohao.weichat.entity.ChatMsg;
import com.caohao.weichat.entity.MyFriends;
import org.springframework.stereotype.Repository;

@Repository("myFriendsDao")
public interface MyFriendsDao extends BaseMapper<MyFriends> {
}
