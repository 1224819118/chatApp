package com.caohao.weichat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caohao.weichat.dao.ChatMsgDao;
import com.caohao.weichat.entity.ChatMsg;
import org.springframework.stereotype.Service;

@Service("chatMsgService")
public class ChatMsgService extends ServiceImpl<ChatMsgDao, ChatMsg> {
}
