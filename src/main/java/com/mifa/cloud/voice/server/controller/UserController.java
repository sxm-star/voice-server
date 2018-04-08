package com.mifa.cloud.voice.server.controller;

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

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String addUser(){
        return "test";
    }
}
