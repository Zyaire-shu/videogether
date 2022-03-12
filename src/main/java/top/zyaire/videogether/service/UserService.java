package top.zyaire.videogether.service;

import top.zyaire.videogether.domain.User;


public interface UserService {
    boolean loginCheck(String username,String password);
    int userCheck(String username);
    boolean insertUser(User record);
    boolean updateUser(User record);
    User selectUser(String username);
    boolean deleteUser(int userid);
}
