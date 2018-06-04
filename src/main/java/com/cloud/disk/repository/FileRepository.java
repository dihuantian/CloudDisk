package com.cloud.disk.repository;

import com.cloud.disk.domain.File;
import com.cloud.disk.domain.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 15:59
 */
public interface FileRepository extends BaseRepository<File, Long> {
    List<File> findAllByUserAndAndClassify(User user, String classify);

    List<File> findAllByUser(User user);
}
