package com.cloud.disk.config.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/5/18
 * Time: 19:00
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400 * 30)//设置Session失效时间,单位是秒s
public class HttpSessionConfig {
}
