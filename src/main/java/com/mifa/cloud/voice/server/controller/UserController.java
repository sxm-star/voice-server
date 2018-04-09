package com.mifa.cloud.voice.server.controller;

import com.mifa.cloud.voice.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sxm
 * @date: 2018/4/8 16:17
 * @version: v1.0.0
 */
@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 测试demo
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Object findUser(@PathVariable("id") String id){
        return userService.findUserById(id);
    }
}
