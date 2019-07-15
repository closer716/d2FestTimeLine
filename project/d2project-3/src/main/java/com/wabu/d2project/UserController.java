package com.wabu.d2project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 
@Controller
public class UserController {
 
    @Autowired
    UserService testService;
    
    @RequestMapping("/query")
    public @ResponseBody List<User> query() throws Exception{
        return testService.getAll();
    }
 
}