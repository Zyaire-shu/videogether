package top.zyaire.videogether.listener;

import org.springframework.context.event.EventListener;
import top.zyaire.videogether.domain.User;
import top.zyaire.videogether.utils.StaticUtils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

@WebListener
public class OnlineUserListener implements HttpSessionAttributeListener {

    private String name;
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        //System.out.println("添加了一个sesson属性");
        HttpSession session=httpSessionBindingEvent.getSession();
        //String id=session.getId()+session.getCreationTime();
        session.setMaxInactiveInterval(-1);//设置session三个小时后超时
        //User a  = (User) session.getAttribute("logedUser");
        //name = a.getUsername();
        //StaticUtils.onlineUser.put(name, a);//添加用户
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        //System.out.println("消除了一个session属性");
        HttpSession session=httpSessionBindingEvent.getSession();
        synchronized(this){
            System.out.println("session失效了");
            //StaticUtils.onlineUser.remove(name);//从用户组中移除掉，用户组为一个map
        }
    }



}
