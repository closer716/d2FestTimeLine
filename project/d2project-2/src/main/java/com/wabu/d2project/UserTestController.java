package com.wabu.d2project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class UserTestController {
 
    @GetMapping("/test")
    public String getUser(Model model) {
        User user = new User("kkaok", "Å×½ºÆ®", "web") ;
        model.addAttribute("user", user);
        return "test";
    }
}
