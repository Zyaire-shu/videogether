package top.zyaire.videogether;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.zyaire.videogether.domain.RtmpYml;
import top.zyaire.videogether.entity.Link;
import top.zyaire.videogether.entity.RtmpEntity;
import top.zyaire.videogether.utils.StaticUtils;
import top.zyaire.videogether.video.VideoHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class UtilTest {
    @Test
    void testCommand() throws IOException, InterruptedException {
        VideoHandler a = new VideoHandler("D:\\SDK\\ffmpeg\\bin");
        RtmpEntity<String,Process>  f= a.videoToRtmp("D:\\迅雷下载\\視頻\\aa.mkv");
        //D:\迅雷下载\視頻
        Thread.sleep(50000);
        System.out.println(f.getKey());
        f.getValue().destroy();
        System.out.println("结束");
    }
    @Test
    void thTest(){
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        PumpStreamHandler psh = new PumpStreamHandler(stdout);
        CommandLine cl = CommandLine.parse("cmd.exe /c dir");
        DefaultExecutor exec = new DefaultExecutor();
        exec.setStreamHandler(psh);
        try {
            exec.execute(cl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(stdout.toString());
    }
    @Test
    void testFfmpeg(){
        VideoHandler vt = new VideoHandler("D:\\SDK\\ffmpeg\\bin");
        try {
            vt.videoToRtmp("D:\\迅雷下载\\視頻\\加勒比海盗2：聚魂棺.Pirates.Of.The.Caribbean.Dead.Mans.Chest.2006.1080p.BluRay.DTS.x264-7bt\\bb.mp4","0","2000k");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("结束");
    }

    @Test
    void md5(){

        System.out.println(StaticUtils.randomString());
    }

    @Test
    void ymlRead(){
        readConfig();
        System.out.println(RtmpYml.INSTANCE.getRtmpAddress());
        System.out.println(RtmpYml.INSTANCE.getFfmpegThreads());
    }
    private static void readConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            File file = new File("./videogether.yml");
            RtmpYml cfg = mapper.readValue(file, RtmpYml.class);
            System.out.println("MyLive read configuration as : {}"+ cfg);
            RtmpYml.INSTANCE = cfg;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    void testLink(){
        readConfig();
        setVideoPath();
        for (String a:RtmpYml.INSTANCE.getVideoPath()){
            System.out.println(a);
        }
        for (Link a:
             StaticUtils.videoRoot) {
            System.out.println(a);
        }
    }

    private static void setVideoPath(){
        for (String root:
                RtmpYml.INSTANCE.getVideoPath()) {
            try {
                List<Link> path = Link.build(root);
                StaticUtils.videoRoot.add(path.get(path.size()-1));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

}
