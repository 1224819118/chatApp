package com.caohao.weichat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caohao.weichat.entity.Users;
import org.springframework.stereotype.Repository;

@Repository("usersDao")
public interface UsersDao extends BaseMapper<Users> {

}
