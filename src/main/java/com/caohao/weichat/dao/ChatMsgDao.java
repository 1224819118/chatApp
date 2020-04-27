package com.caohao.weichat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caohao.weichat.entity.ChatMsg;
import org.springframework.stereotype.Repository;

@Repository("chatMsgDao")
public interface ChatMsgDao extends BaseMapper<ChatMsg> {
}
