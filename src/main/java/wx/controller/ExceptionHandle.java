package wx.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import wx.util.Result;

import java.util.*;
@RestControllerAdvice
public class ExceptionHandle {

    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    }

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
/*
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception ex) {
        Map map = new HashMap();
        map.put("code", 500);
        map.put("msg", ex.getMessage());
        return new Result(map,"后端出现异常",2);
    }
*/
}
