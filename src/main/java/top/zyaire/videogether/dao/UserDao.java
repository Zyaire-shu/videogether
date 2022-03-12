package top.zyaire.videogether.dao;

import org.springframework.stereotype.Repository;
import top.zyaire.videogether.domain.User;

@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    User selectByUserName(String username);

    int selectUserCount(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkLogin(String name,String pass);
}