package top.zyaire.videogether.service.impl;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import top.zyaire.videogether.domain.RtmpYml;
import top.zyaire.videogether.entity.FileEntity;
import top.zyaire.videogether.service.VideoService;
import top.zyaire.videogether.utils.StaticUtils;
import top.zyaire.videogether.video.VideoHandler;

import javax.swing.plaf.IconUIResource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author itning
 * @date 2019/7/16 14:14
 */
@Service
public class VideoServiceImpl implements VideoService {
    private static final String[] VIDEO_SUFFIX = new String[]{"mp4", "avi", "3gp", "wmv", "mkv", "mpeg", "rmvb"};
    private static final String[] SKIP_SUFFIX = new String[]{"torrent","xltd","cfg"};
    @Override
    public List<FileEntity> getFileEntities(String location) {
        File[] files;
        if (location == null) {
            files = File.listRoots();
        } else {
            File file = new File(location);
            files = file.listFiles();
        }
        List<FileEntity> fileEntities;
        if (files != null) {
            fileEntities = new ArrayList<>(files.length);
            for (File f : files) {
                if (!isVideoFile(f.getName()) && f.isFile()){//如果不是视频并且是文档就跳过
                    continue;
                }
                FileEntity fileEntity = new FileEntity();
                fileEntity.setName(f.getName());
                fileEntity.setSize(FileUtils.byteCountToDisplaySize(f.length()));
                fileEntity.setFile(f.isFile());
                fileEntity.setCanPlay(isVideoFile(f.getName()));
                fileEntity.setLocation(f.getPath());
                fileEntities.add(fileEntity);
            }
        } else {
            fileEntities = Collections.emptyList();
        }
        return fileEntities
                .parallelStream()
                .sorted((o1, o2) -> {
                    if (o1.isFile() && !o2.isFile()) {
                        return 1;
                    } else if (!o1.isFile() && o2.isFile()) {
                        return -1;
                    } else {
                        return o1.getName().compareTo(o2.getName());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public void videoToRtsp(String location) {//将视频转换为rtsp直播，暂时用不到
        VideoHandler vt = new VideoHandler(RtmpYml.INSTANCE.getFfmpegPath());
        //vt.videoToRtsp(location);
    }

    @Override
    public void videoToRtmp(String location) {
        VideoHandler vt = new VideoHandler(RtmpYml.INSTANCE.getFfmpegPath());
        try {
            StaticUtils.rtmpList.put(location,vt.videoToRtmp(location));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void videoToRtmp(String location, String startTime) {
        VideoHandler vt = new VideoHandler(RtmpYml.INSTANCE.getFfmpegPath());
        try {
            StaticUtils.rtmpList.put(location,vt.videoToRtmp(location,startTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isSkipFile(String name){
        String suffix = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        for (String s : SKIP_SUFFIX) {
            if (s.equals(suffix)) {
                return true;
            }
        }
        return false;
    }
    private boolean isVideoFile(String name) {
        String suffix = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        for (String s : VIDEO_SUFFIX) {
            if (s.equals(suffix)) {
                return true;
            }
        }
        return false;
    }
}
