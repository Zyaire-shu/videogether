package top.zyaire.videogether;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import top.zyaire.videogether.domain.RtmpYml;
import top.zyaire.videogether.entity.Link;
import top.zyaire.videogether.utils.StaticUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

@MapperScan("top.zyaire.videogether.dao")
@ServletComponentScan()
@SpringBootApplication
public class VideogetherApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideogetherApplication.class, args);
        readConfig();
        setVideoPath();
    }
    private static void readConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            File file = new File("./videogether.yml");

            RtmpYml cfg = mapper.readValue(file, RtmpYml.class);
            //System.out.println("MyLive read configuration as : {}"+ cfg);
            RtmpYml.INSTANCE = cfg;
        } catch (Exception e) {
            throw new RuntimeException(e);
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
