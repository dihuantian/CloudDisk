package com.cloud.disk.repository;

import com.cloud.disk.domain.FolderContact;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/25
 * Time: 10:07
 */
public interface FolderContactRepository extends BaseRepository<FolderContact, Long> {
    Optional<FolderContact> findAllByFolder_FolderId(long folderId);
}
