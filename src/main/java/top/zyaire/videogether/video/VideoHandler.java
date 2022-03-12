package top.zyaire.videogether.video;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.zyaire.videogether.domain.RtmpYml;
import top.zyaire.videogether.entity.RtmpEntity;
import top.zyaire.videogether.utils.StaticUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static  top.zyaire.videogether.utils.CommandUtils.*;


public class VideoHandler {
    //ffmpeg -re -i .\aa.mkv -vcodec copy -codec copy -rtsp_transport tcp -f rtsp rtsp://127.0.0.1/live/abc
    private String pathToFfmpeg;

    public VideoHandler(){}
    public VideoHandler(String pathToFfmpeg) {
        this.pathToFfmpeg = pathToFfmpeg + File.separator + "ffmpeg";
    }
//region
//    public void videoToRtsp(String location,String time,String biteRate){
//        List<String> toRtsp = new ArrayList<>();
//        toRtsp.add("ffmpeg");
//        toRtsp.add("-re");
//        toRtsp.add("-ss");
//        toRtsp.add(time);
//        toRtsp.add("-i");
//        toRtsp.add(location);
//        toRtsp.add("-vcodec");
//        toRtsp.add("copy");
//        toRtsp.add("-b:v");
//        toRtsp.add(biteRate);
//        toRtsp.add("-rtsp_transport");
//        toRtsp.add("tcp");
//        toRtsp.add("-f");
//        toRtsp.add("rtsp");
//        toRtsp.add("rtsp://127.0.0.1/live/"+location.substring(location.lastIndexOf(File.separator)+1,location.lastIndexOf(".")));
//        new Thread(() -> {
//            try {
//                //process(toRtsp, System.out::println);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//       // return true;
//    }
//    public void videoToRtsp(String location){
//        videoToRtsp(location,"0","2000k");
//    }
    public RtmpEntity<String,Process> videoToRtmp(String location, String time, String biteRate) throws IOException {
        String address = "127.0.0.1:1935";
        if (RtmpYml.INSTANCE!=null){
            address = RtmpYml.INSTANCE.getRtmpAddress()+":"+RtmpYml.INSTANCE.getRtmpPort();
        }
        address = "rtmp://"+address+"/live/"+StaticUtils.randomString();
        RtmpEntity<String,Process> rtmpMap = new RtmpEntity<>();
        List<String> toRtmp = new ArrayList<>();
        toRtmp.add("ffmpeg");
        toRtmp.add("-threads");
        toRtmp.add(RtmpYml.INSTANCE.getFfmpegThreads());
        toRtmp.add("-re");
        toRtmp.add("-ss");
        toRtmp.add(time);
        toRtmp.add("-i");
        toRtmp.add(location);
        toRtmp.add("-vcodec");
        toRtmp.add("libx264");
        toRtmp.add("-b:v");
        toRtmp.add(biteRate);
        toRtmp.add("-f");
        toRtmp.add("flv");
        toRtmp.add(address);
        Process trmpProcess = process(toRtmp,line->{
           // System.out.println(line);
        });
        System.out.println("地址："+address);
        rtmpMap.put(address,trmpProcess);//把地址和Process传入HashMap保存，以便停止
        return rtmpMap;
    }
    public RtmpEntity<String, Process> videoToRtmp(String location) throws IOException {
       return videoToRtmp(location,"0","2000k");
    }
    public RtmpEntity<String, Process> videoToRtmp(String location, String startTime) throws IOException {
        return videoToRtmp(location,startTime,"2000k");
    }
}
