package top.zyaire.videogether.controller;

import com.alibaba.fastjson.JSONObject;

import com.google.common.collect.ImmutableMap;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.zyaire.videogether.domain.RtmpYml;
import top.zyaire.videogether.domain.User;
import top.zyaire.videogether.domain.Watchrecord;
import top.zyaire.videogether.service.VideoService;
import top.zyaire.videogether.service.WatchRecordService;
import top.zyaire.videogether.utils.StaticUtils;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;

@Controller
public class VideoController {

    private VideoService videoService;
    private WatchRecordService watchRecordService;

    @Autowired
    public void setService(VideoService videoService, WatchRecordService watchRecordService){
        this.videoService = videoService;
        this.watchRecordService = watchRecordService;
    }

    @ResponseBody
    @CrossOrigin//开启直播流的接口，使用Ajax传入location
    @RequestMapping(value = "/stream",method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String convertVideo(@RequestBody JSONObject jsonObject) throws UnsupportedEncodingException {
        boolean flag = false;
        JSONObject jb = new JSONObject();
        String videoLocation = jsonObject.getString("videoLocation");
        String startTime = jsonObject.getString("startTime");
        String rtmpAddress = null;
        //必须是视频列表有视频
        if (StaticUtils.rtmpList.get(videoLocation)!=null) {//如果已存在视频流就返回true
            jb.put("isSuccess",true);
            jb.put("sMessage","已存在视频流，获取成功");
        }else if(StaticUtils.rtmpList.size()>=3) {//如果已存在视频流大于三个直接停止推流
            jb.put("isSuccess", false);
            jb.put("sMessage","已有三条视频流，不允许开启视频流");
            return jb.toJSONString();
        }else if(StaticUtils.rtmpList.get(videoLocation)==null) {//如果视频不存在推流，那么开启推流
            videoService.videoToRtmp(videoLocation,startTime);//把文件地址转换为rtmp视频流
            if (StaticUtils.rtmpList.get(videoLocation)!=null) {//推流之后如果获取到就代表推流成功
                jb.put("isSuccess",true);
                jb.put("sMessage","推流成功");
                rtmpAddress = StaticUtils.rtmpList.get(videoLocation).getKey();//获取rtmp地址，因为需要更具rtmp地址转化为http地址
                String httpAddress = "http://"+ RtmpYml.INSTANCE.getRtmpAddress()+":"+RtmpYml.INSTANCE.getHttpFlvPort()+"/live/"+rtmpAddress.substring(rtmpAddress.lastIndexOf('/')+1);
                //System.out.println(httpAddress);
                jb.put("videoUrl",httpAddress);
            }else {
                jb.put("isSuccess",false);
                jb.put("sMessage","推流失败");
                //return jb.toJSONString();
            }
        } else{//列表没有视频代表推流失败
            jb.put("isSuccess",false);
            jb.put("sMessage","推流失败");
            return jb.toJSONString();
        }

        return jb.toJSONString();
    }

    @ResponseBody//播放直播流的接口
    @RequestMapping(value = "/playStream",method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String playStream(@RequestBody JSONObject jsonObject) throws UnsupportedEncodingException {
        JSONObject jb = new JSONObject();
        String videoLocation = jsonObject.getString("videoLocation");
        String videoSeek =  jsonObject.getString("videoSeek");
        if (StaticUtils.rtmpList.get(videoLocation)==null) {//如果已存在视频流就返回true
            jb.put("isSuccess",false);
            jb.put("sMessage","获取失败");
            return jb.toJSONString();
        }
        String rtmpAddress = null;
        rtmpAddress = StaticUtils.rtmpList.get(videoLocation).getKey();//获取rtmp地址，因为需要更具rtmp地址转化为http地址
        String httpAddress = "http://"+ RtmpYml.INSTANCE.getRtmpAddress()+":"+RtmpYml.INSTANCE.getHttpFlvPort()+"/live/"+rtmpAddress.substring(rtmpAddress.lastIndexOf('/')+1);
        //System.out.println(httpAddress);
        jb.put("isSuccess",true);
        jb.put("videoUrl",httpAddress);
        return jb.toJSONString();
    }

    @ResponseBody//关闭直播流的接口
    @RequestMapping(value = "/kill",method = RequestMethod.POST, produces="application/json;charset=UTF-8")//结束视频推流
    public String proxy(@RequestBody JSONObject jsonObject, HttpSession session){
        String location = jsonObject.getString("location");//结束视频的地址
        JSONObject jb = new JSONObject();
        System.out.println("路径是"+location);
        Process p = StaticUtils.rtmpList.get(location).getValue();//根据地址获取进程
        System.out.println("p为空"+p==null);
        if(p==null){
            jb.put("isKill",true);
            return jb.toJSONString();
        }
        p.destroy();//销毁进程
        StaticUtils.rtmpList.remove(location);
        jb.put("isKill",true);
        User name = (User) session.getAttribute("logedUser");
        watchRecordService.insertWatchRecord(new Watchrecord(location,name.getUsername()));
        return jb.toJSONString();
    }
}
