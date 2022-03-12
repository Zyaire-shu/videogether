package top.zyaire.videogether.controller;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.zyaire.videogether.domain.User;
import top.zyaire.videogether.service.UserService;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @CrossOrigin//用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String login(@RequestBody JSONObject jsonObject, HttpSession session) {
        String name = jsonObject.getString("userName");
        String password = jsonObject.getString("passWord");
        boolean success = userService.loginCheck(name, password);//登录查询
        JSONObject result = new JSONObject();
        if (success) {//如果用户名密码匹配正确
            User user = userService.selectUser(name);
            session.setAttribute("logedUser", user);//如果用户名和密码匹配成功，那么就根据用户名查询用户，然后放到session中
            result.put("isSuccess", true);
            result.put("sMessage", "登录成功！");//返回成功
            return result.toJSONString();
        }
        result.put("isSuccess", false);
        result.put("sMessage", "登录失败，请检查用户名和密码");//返回失败

        return result.toJSONString();
    }

    @ResponseBody
    @CrossOrigin//用户注册
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String signup(@RequestBody JSONObject jsonObject, HttpSession session) {
        User register = new User(jsonObject.getString("userName"), jsonObject.getString("passWord"), "user");
        boolean result = userService.insertUser(register);
        JSONObject jb = new JSONObject();
        if (result) {
            session.setAttribute("logedUser", register);//注册成功之后登录
        }
        jb.put("isSuccess", result);
        return jb.toJSONString();
    }

    @ResponseBody
    @CrossOrigin//检测是否存在该用户
    @RequestMapping(value = "/check", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String count(@RequestBody JSONObject jsonObject) {
        //System.out.println("接收到的JSON字符串"+jsonObject.toJSONString());
        String user = jsonObject.getString("userName").replace(" ", "");
        //System.out.println(user);
        int count = userService.userCheck(user);//根据用户名查找是否已经存在用户
        JSONObject jb = new JSONObject();
        jb.put("userCount", count);
        return jb.toJSONString();
    }

    @RequestMapping(value = "/logout")//登出
    public String logout(HttpSession session) {
        session.removeAttribute("logedUser");
        return "redirect:/";
    }
}
