package com.caohao.weichat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caohao.weichat.entity.ChatMsg;
import com.caohao.weichat.entity.FriendsRequest;
import org.springframework.stereotype.Repository;

@Repository("friendsRequestDao")
public interface FriendsRequestDao extends BaseMapper<FriendsRequest> {
}
