package top.zyaire.videogether.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * WatchRecord
 * @author 
 */
@Data
public class Watchrecord implements Serializable {
    private Integer id;

    private String path;

    private Integer time;

    private String user;

    public Watchrecord() {
    }

    public Watchrecord(String path, Integer time, String user) {
        this.path = path;
        this.time = time;
        this.user = user;
    }

    public Watchrecord(String path, String user) {
        this.path = path;
        this.user = user;
    }

    private static final long serialVersionUID = 1L;
}