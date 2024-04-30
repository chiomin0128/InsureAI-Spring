package com.Insureai.Insure_ai.controller;

import com.Insureai.Insure_ai.jpa.dto.UserDTO;
import com.Insureai.Insure_ai.service.Member.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public String UserJoin(UserDTO userDTO){
        userService.UserJoin(userDTO);
        return "ok";
    }

}
