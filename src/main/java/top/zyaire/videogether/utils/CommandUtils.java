package top.zyaire.videogether.utils;

import lombok.Data;
import org.apache.commons.exec.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author itning
 * @date 2019/7/17 20:56
 */
public class CommandUtils{

    public static Process process(List<String> command ,Consumer<String> commandInfo) throws IOException {
        System.out.println("开始执行");
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        Process pr = builder.start();
        new Thread(()->{
            try (BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()))) {
                String line;
                System.out.println("命令执行之前");
                while ((line = br.readLine()) != null) {
                    //System.out.println("执行命令"+line);
                    commandInfo.accept(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();//开启一个新的线程用于接受执行命令的输出
        return pr;
    }

    public static boolean process(CommandLine stringCommandLine){

        return false;
    }
}
