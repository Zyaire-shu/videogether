package top.zyaire.videogether.dao;

import org.springframework.stereotype.Repository;
import top.zyaire.videogether.domain.Watchrecord;

import java.util.List;

@Repository
public interface WatchrecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Watchrecord record);

    int insertSelective(Watchrecord record);

    Watchrecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Watchrecord record);

    int updateByPrimaryKey(Watchrecord record);

    List<Watchrecord> allRecord();
}