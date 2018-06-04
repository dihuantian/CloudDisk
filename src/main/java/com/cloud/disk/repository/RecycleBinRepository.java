package com.cloud.disk.repository;

import com.cloud.disk.domain.File;
import com.cloud.disk.domain.RecycleBin;
import com.cloud.disk.domain.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/6/1
 * Time: 2:13
 */
public interface RecycleBinRepository extends BaseRepository<RecycleBin, Long> {
    List<RecycleBin> findAllByUser(User user);
}
