package com.cloud.disk.repository;

import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 12:32
 */
public interface BaseRepository<T, ID> extends CrudRepository<T, ID> {
}
