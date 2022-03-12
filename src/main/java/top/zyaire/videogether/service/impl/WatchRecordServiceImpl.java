package top.zyaire.videogether.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zyaire.videogether.dao.WatchrecordDao;
import top.zyaire.videogether.domain.Watchrecord;
import top.zyaire.videogether.service.WatchRecordService;

import java.util.List;

/**
 * @Author ZyaireShu
 * @Date 2022/1/29 16:14
 * @Version 1.0
 */
@Service("recordService")
public class WatchRecordServiceImpl implements WatchRecordService {
    private WatchrecordDao watchrecordDao;

    @Autowired
    public void setWatchrecordDao(WatchrecordDao watchrecordDao){
        this.watchrecordDao = watchrecordDao;
    }
    @Override
    public List<Watchrecord> getWatchRecords() {
        return watchrecordDao.allRecord();
    }

    @Override
    public boolean insertWatchRecord(Watchrecord watchrecord) {
        return watchrecordDao.insert(watchrecord)==1;
    }
}
