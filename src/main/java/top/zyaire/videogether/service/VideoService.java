package top.zyaire.videogether.service;


import top.zyaire.videogether.entity.FileEntity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author itning
 * @date 2019/7/16 14:12
 */
public interface VideoService {

    /**
     * 获取文件列表
     *
     * @param location 路径
     * @return 文件列表
     */
    List<FileEntity> getFileEntities(String location);

    void videoToRtsp(String location);
    void videoToRtmp(String location);
    void videoToRtmp(String location,String startTime);
}
