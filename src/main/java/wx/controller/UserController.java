package wx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wx.util.Result;
@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/profile")
    public Result profile(){
        return new Result(null,"",0);
    }
}
