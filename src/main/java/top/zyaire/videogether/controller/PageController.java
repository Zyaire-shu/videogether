package top.zyaire.videogether.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.zyaire.videogether.domain.RtmpYml;
import top.zyaire.videogether.domain.User;
import top.zyaire.videogether.domain.Watchrecord;
import top.zyaire.videogether.entity.FileEntity;
import top.zyaire.videogether.entity.Link;
import top.zyaire.videogether.service.VideoService;
import top.zyaire.videogether.service.WatchRecordService;
import top.zyaire.videogether.utils.StaticUtils;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    private final VideoService videoService;
    private WatchRecordService watchRecordService;

    @Autowired
    public PageController(VideoService videoService,WatchRecordService watchRecordService) {
        this.watchRecordService = watchRecordService;
        this.videoService = videoService;
    }

    @RequestMapping("/")//网站主页
    public String index(Model model, String location, HttpSession session) throws UnsupportedEncodingException {
        List<FileEntity> fileEntityList = new ArrayList<>();
        if (location != null) {//
            boolean found = false;
            List<Link> linkList = Link.build(location);
            for (Link j :StaticUtils.videoRoot) {//从设置好的视频路径选择
                if (j.isMyChild(linkList.get(linkList.size()-1))){
                   found = true;//如果确实是某一路径的子路径，就找到了
                }
            }
            if (found) {//找到之后允许访问
                model.addAttribute("links",linkList);
                fileEntityList = videoService.getFileEntities(location);
            }else {
                return "redirect:/";//没有找到，重定向到网站首页
            }
        }else {
            String[] paths = RtmpYml.INSTANCE.getVideoPath();
            for (String a:
                 paths) {
                 fileEntityList.addAll(videoService.getFileEntities(a));
            }
        }
        User currentUser =  (User) session.getAttribute("logedUser");
        model.addAttribute("currentUser",currentUser);//将用户传到前端以判定权限
        model.addAttribute("files", fileEntityList);
        return "index";
    }

    @RequestMapping(value = "/login")//登录界面
    public String login(Model model, HttpSession session){
        User currentUser =  (User) session.getAttribute("logedUser");
        model.addAttribute("currentUser",currentUser);
        return "user/login";
    }

    @RequestMapping(value = "/register")//注册界面
    public String signup(){
        return "user/register";
    }

    @RequestMapping(value = "/streamList")//直播流列表
    public String streamList(Model model,HttpSession session){
        User currentUser =  (User) session.getAttribute("logedUser");
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("lists", StaticUtils.rtmpList);
        return "list";
    }

    @RequestMapping("/video")//视频播放参数选择界面
    public String video(@RequestParam(value = "location") String location, Model model,HttpSession session) throws UnsupportedEncodingException {
        if (location != null) {
            model.addAttribute("links", Link.build(location));
        }
        User currentUser =  (User) session.getAttribute("logedUser");
        model.addAttribute("currentUser",currentUser);//将用户传到前端以判定权限
        model.addAttribute("location",location);
        return "video";
    }

    @RequestMapping("/records")
    public String watched(Model model){
        List<Watchrecord> watchrecords = watchRecordService.getWatchRecords();
        model.addAttribute("records",watchrecords);
        return "records";
    }
}
