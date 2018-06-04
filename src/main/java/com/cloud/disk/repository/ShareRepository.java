package com.cloud.disk.repository;

import com.cloud.disk.domain.File;
import com.cloud.disk.domain.Share;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 16:16
 */
public interface ShareRepository extends BaseRepository<Share, Long> {

    Share findAllByFile(File file);

    List<Share> findAllByFileIn(Iterable<File> fileId);
}
