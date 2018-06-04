package com.cloud.disk.config.task;

import ch.qos.logback.core.rolling.helper.RollingCalendar;
import com.cloud.disk.domain.RecycleBin;
import com.cloud.disk.service.FileOperationService;
import com.cloud.disk.unit.CronDateShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.util.BuddhistCalendar;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 覃玉初
 * Date: 2018/6/1
 * Time: 22:52
 */
@Component
@Configuration
public class RecycleBinTask {

    @Autowired
    private FileOperationService fileOperationService;

    /**
     * 回收站删除回收垃圾
     */
    @Scheduled(cron = "00 00 00 * * ?")
    public void deleteRecycleBin() {
        List<RecycleBin> recycleBins = fileOperationService.getDeleteRecycleBinFile();
        for (RecycleBin recycleBin : recycleBins) {
            Calendar date = Calendar.getInstance(Locale.CHINA);
            Calendar currentCalendar = Calendar.getInstance(Locale.CHINA);
            currentCalendar.setTime(new Date());
            date.setTime(CronDateShift.cronSwitchDate(recycleBin.getSurplusDateTime()));
            if (CronDateShift.dateEquality(currentCalendar, date)) {
                fileOperationService.deleteFile(recycleBin);
            }
        }
    }
}
