package com.yjx.crm.controller;

import com.yjx.crm.base.BaseController;
import com.yjx.crm.dao.PermissionMapper;
import com.yjx.crm.service.PermissionService;
import com.yjx.crm.service.UserService;
import com.yjx.crm.utils.LoginUserUtil;
import com.yjx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {
    @Resource
    private UserService userService;
    @Resource
    private PermissionService permissionService;
    /**
     * 系统登录⻚
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    // 系统界⾯欢迎⻚
    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    /**
     * 后端管理主⻚⾯
     * @return
     */
//    @RequestMapping("main")
//    public String main(HttpServletRequest request) {
//        // 通过⼯具类，从cookie中获取userId
//        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
//        // 调⽤对应Service层的⽅法，通过userId主键查询⽤户对象
//        User user = userService.selectByPrimaryKey(userId);
//        // 将⽤户对象设置到request作⽤域中
//        request.getSession().setAttribute("user", user);
//        return "main";
//    }

    /**
     * 后端管理主⻚⾯
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        request.getSession().setAttribute("user",userService.selectByPrimaryKey(userId));
        List<String> permissions=permissionService.queryUserHasRolesHasPermissions(userId);
        request.getSession().setAttribute("permissions",permissions);
        return "main";
    }

}
