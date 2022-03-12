package top.zyaire.videogether.domain;

import lombok.Data;

@Data
public class RtmpYml {
    public static RtmpYml INSTANCE = null;

    private int rtmpPort;
    private String rtmpAddress;
    private int httpFlvPort;
    private String ffmpegPath;
    private String[] videoPath;
    private String ffmpegThreads;
}
