package com.cloud.disk.repository;

import com.cloud.disk.domain.Folder;
import com.cloud.disk.domain.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 14:27
 */
public interface FolderRepository extends BaseRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
}
