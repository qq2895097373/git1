package com.yjx.crm.controller;

import com.yjx.crm.base.BaseController;
import com.yjx.crm.base.ResultInfo;
import com.yjx.crm.exceptions.ParamsException;
import com.yjx.crm.model.UserModel;
import com.yjx.crm.query.UserQuery;
import com.yjx.crm.service.UserService;
import com.yjx.crm.utils.LoginUserUtil;
import com.yjx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class UserController  extends BaseController {
    @Resource
    private UserService userService;

    /**
     * 查询所有的销售⼈员
     * @return
     */
    @RequestMapping("user/queryAllSales")
    @ResponseBody
    public List<Map<String, Object>> queryAllSales() {
        return userService.queryAllSales();
    }

    /**
     * ⽤户登录
     *
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("/user/login")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd) {
        ResultInfo resultInfo = new ResultInfo();
        // 通过 try catch 捕获 Service 层抛出的异常
//        try {
            System.out.println("login");
            // 调⽤Service层的登录⽅法，得到返回的⽤户对象
            UserModel userModel = userService.userLogin(userName, userPwd);
            /**
             * 登录成功后，有两种处理：
             *  1. 将⽤户的登录信息存⼊ Session （ 问题：重启服务器，Session 失效，客户端需要重复登录 ）
             *  2. 将⽤户信息返回给客户端，由客户端（Cookie）保存
             */
            // 将返回的UserModel对象设置到 ResultInfo 对象中
            resultInfo.setResult(userModel);
//        } catch (ParamsException e) { // ⾃定义异常
//            e.printStackTrace();
//            // 设置状态码和提示信息
//            resultInfo.setCode(e.getCode());
//            resultInfo.setMsg(e.getMsg());
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultInfo.setCode(500);
//            resultInfo.setMsg("操作失败！");
//        }
        return resultInfo;
    }

    /**
     * ⽤户密码修改
     *
     * @param request
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
    @PostMapping("/user/updatePassword")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request,
                                         String oldPassword, String newPassword, String confirmPassword) {
        ResultInfo resultInfo = new ResultInfo();
//        // 通过 try catch 捕获 Service 层抛出的异常
//        try {
            // 获取userId
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            // 调⽤Service层的密码修改⽅法
            userService.updateUserPassword(userId, oldPassword, newPassword, confirmPassword);
//        } catch (ParamsException e) { // ⾃定义异常
//            e.printStackTrace();
//            // 设置状态码和提示信息
//            resultInfo.setCode(e.getCode());
//            resultInfo.setMsg(e.getMsg());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultInfo.setCode(500);
//            resultInfo.setMsg("操作失败！");
//        }
        return resultInfo;
    }

    @RequestMapping("user/toPasswordPage")
    public String toPasswordPage(){
        return "user/password";
    }

    /**
     * 多条件查询⽤户数据
     * @param userQuery
     * @return
     */
    @RequestMapping("user/list")
    @ResponseBody
    public Map<String, Object> queryUserByParams(UserQuery userQuery) {
        return userService.queryUserByParams(userQuery);
    }
    /**
     * 进⼊⽤户⻚⾯
     * @return
     */
    @RequestMapping("user/index")
    public String index(){
        return "user/user";
    }
    /**
     * 添加⽤户
     * @param user
     * @return
     */
    @RequestMapping("/user/save")
    @ResponseBody
    public ResultInfo saveUser(User user){
        userService.saveUser(user);
        return success("用户添加成功！");
    }
    /**
     * 更新⽤户
     * @param user
     * @return
     */
    @RequestMapping("/user/update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        System.out.println(user.getId()+"========================="+user.getRoleIds());
        userService.updateUser(user);
        return success("用户更新成功！");
    }

    /**
     * 进⼊⽤户添加或更新⻚⾯
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/user/addOrUpdateUserPage")
    public String addUserPage(Integer id , Model model){
        if (null != id ){
            model.addAttribute("user1",userService.selectByPrimaryKey(id));
        }
        return "user/add_update";
    }
    /**
     * 删除⽤户
     * @param ids
     * @return
     */
    @RequestMapping("user/delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteUserByIds(ids);
        return success("用户记录删除成功");
    }

}
