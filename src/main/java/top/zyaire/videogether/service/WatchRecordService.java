package top.zyaire.videogether.service;

import top.zyaire.videogether.domain.Watchrecord;

import java.util.List;

/**
 * @Author ZyaireShu
 * @Date 2022/1/29 16:04
 * @Version 1.0
 */
public interface WatchRecordService {
    List<Watchrecord> getWatchRecords();
    boolean insertWatchRecord(Watchrecord watchrecord);
}
