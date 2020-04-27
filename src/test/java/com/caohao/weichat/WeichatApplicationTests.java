package com.caohao.weichat;

import com.caohao.weichat.entity.Users;
import com.caohao.weichat.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeichatApplicationTests {
    @Autowired
    UsersService usersService;

    @Test
    void contextLoads() {
        Users users = new Users();
        users.setUserName("testinsert");
        users.setNickName("testinsert");
        users.setPassword("testinsert");
        users.setFaceImage("testinsert");
        users.setFaceImageBig("testinsert");
        users.setQrcode("testinsert");
        users.setCid("testinsert");
        boolean save = usersService.save(users);
        System.out.println(save);
    }

}
