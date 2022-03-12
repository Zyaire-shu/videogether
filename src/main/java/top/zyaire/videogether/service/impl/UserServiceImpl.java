package top.zyaire.videogether.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zyaire.videogether.dao.UserDao;
import top.zyaire.videogether.domain.User;
import top.zyaire.videogether.service.UserService;
import top.zyaire.videogether.utils.StaticUtils;

@Service("userService")
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao( UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public boolean loginCheck(String username, String password) {//登录查看
        username = username.replace(" ","");
        password = password.replace(" ","");//去掉空格
        if ("".equals(username) || "".equals(password))
            return false;//如果为空就返回失败
        password = StaticUtils.getMd5(password);//MD5加密之后去数据库查询有没有密码
        return userDao.checkLogin(username,password)==1;
    }

    @Override
    public int userCheck(String username) {
        username=username.replace(" ","");//去掉用户名中的空格
        if ("".equals(username))
            return 0;
        return userDao.selectUserCount(username);
    }

    @Override
    public boolean insertUser(User record) {
        if ("".equals(record.getUsername().replace(" ","")) || "".equals(record.getPassword().replace(" ","")))
            return false;//用户名密码为空，直接返回失败
        String pass = record.getPassword();
        record.setPassword(StaticUtils.getMd5(pass));//将密码md5加密
        return userDao.insertSelective(record)==1;
    }

    @Override
    public boolean updateUser(User record) {
        return userDao.updateByPrimaryKeySelective(record)==1;
    }

    @Override
    public User selectUser(String username) {
        return userDao.selectByUserName(username);
    }

    @Override
    public boolean deleteUser(int userid) {
        return userDao.deleteByPrimaryKey(userid)==1;
    }
}
