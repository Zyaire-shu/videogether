package top.zyaire.videogether;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import top.zyaire.videogether.dao.UserDao;
import top.zyaire.videogether.domain.User;
import top.zyaire.videogether.entity.FileEntity;
import top.zyaire.videogether.service.UserService;
import top.zyaire.videogether.service.VideoService;
import top.zyaire.videogether.service.impl.VideoServiceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class VideogetherApplicationTests {

    @Value("${rtmpAddress}")
    String address;
   @Autowired
    UserService service;
    @Test
    void contextLoads() {
        service.insertUser(new User("perm1","szw371517577","user"));
        System.out.println(service.loginCheck("perm","szw371517577"));
    }
    @Test
    void test(){
        System.out.println(address);
    }

    @Test
    void mai() throws IOException {
        File file = new File("D:\\WorkSpace\\视频播放器\\videogether\\out\\artifacts\\videogether_jar\\lib");
        File out = new File("D:\\WorkSpace\\视频播放器\\videogether\\src\\main\\resources\\META-INF\\MANIFEST.MF");
        FileWriter fileWriter = new FileWriter(out,true);
        for (File f : file.listFiles()) {
            String opt = " " +"lib/"+ f.getName() + " \n";
            System.out.println(opt);
            fileWriter.write(opt);
        }
        fileWriter.flush();
        fileWriter.close();
    }

}
