package com.cloud.disk.repository;

import com.cloud.disk.domain.User;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/9
 * Time: 12:29
 */
public interface UserRepository extends BaseRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}
