package com.caohao.weichat.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caohao.weichat.dao.ChatMsgDao;
import com.caohao.weichat.dao.UsersDao;
import com.caohao.weichat.entity.ChatMsg;
import com.caohao.weichat.entity.FriendsRequest;
import com.caohao.weichat.entity.MyFriends;
import com.caohao.weichat.entity.Users;
import com.caohao.weichat.entity.bo.UsersBo;
import com.caohao.weichat.entity.vo.UsersVo;
import com.caohao.weichat.util.Base64Util;
import com.caohao.weichat.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service("usersService")
@Transactional
public class UsersService extends ServiceImpl<UsersDao, Users> {
    @Autowired
    UsersDao usersDao;
    @Autowired
    FastDFSUtil fdfsUtil;

    /**
     * 登录服务最终返回包装后的vo类型
     * @param users
     * @return
     */
    public UsersVo loginCheck(Users users){
        UsersVo usersVo = null;
        if (users.getUserName()==null||users.getPassword()==null){
            return usersVo;
        }
        QueryWrapper<Users> queryWrapper = new QueryWrapper<Users>();
        queryWrapper.eq("user_name",users.getUserName())
                .eq("password",users.getPassword());
        Users selectOne = usersDao.selectOne(queryWrapper);
        if (selectOne==null){
           return usersVo;
        }
        usersVo = UsersVo.copy(selectOne);
        return usersVo;
    }


    public Users upoadFaceImage(UsersBo usersBo){
        String url = null;
        try {
            MultipartFile multipartFile = Base64Util.base64ToMultipart(usersBo.getFaceData());
            url = fdfsUtil.perfixMultipartFileToUploadImage(multipartFile);
        } catch (IOException e) {
            System.out.println("上传文件失败");
            return null;
        }
        url = "/"+url;
        //上传文件成功的情况下我们对这个用户的头像信息进行更新
        UpdateWrapper<Users> usersUpdateWrapper = new UpdateWrapper<Users>();
        usersUpdateWrapper.eq("id",usersBo.getUserId()).set("face_image",url)
        .set("face_image_big",url);
        usersDao.update(null, usersUpdateWrapper);
        Users users = usersDao.selectById(usersBo.getUserId());
        return users;
    }


    /**
     * 找到好友
     * @param userName
     * @return
     */
    public Users queryUserByUserName(String userName){
        QueryWrapper<Users> queryWrapper = new QueryWrapper<Users>();
        queryWrapper.eq("user_name",userName);
        Users users = usersDao.selectOne(queryWrapper);
        return users;
    }
    /**
     * 通过friendRequest将他转化为对应的users
     */
    public List<Users> friendRequestTransfoToUsers(List<FriendsRequest> friendsRequests){
        List<Users> usersList = new LinkedList<Users>();
        for (FriendsRequest fr:friendsRequests) {
            Users users = usersDao.selectById(fr.getSendUserId());
            if (users != null){
                usersList.add(users);
            }
        }
        return usersList;
    }

    /**
     * 通过Myfriends将他转化为对应的users
     */
    public List<Users> myFriendsTransfoToUsers(List<MyFriends> myFriends){
        List<Users> usersList = new LinkedList<Users>();
        for (MyFriends fr:myFriends) {
            Users users = usersDao.selectById(fr.getMyFriendsUserId());
            if (users != null){
                usersList.add(users);
            }
        }
        return usersList;
    }


}
