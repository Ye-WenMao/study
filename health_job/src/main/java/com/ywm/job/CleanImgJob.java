package com.ywm.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ywm.SetMealService;
import com.ywm.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: YEWENMAO
 * @data: 2020/10/26 10:39
 */
@Component
public class CleanImgJob {

    private static final Logger logger = LoggerFactory.getLogger(CleanImgJob.class);

    @Reference
    private SetMealService setMealService;

    @Scheduled(initialDelay = 3000,fixedDelay = 300000)
    public void cleanImg() {

        // 查出7牛上的s所有图片
        List<String> imgIn7Niu = QiNiuUtils.listFile();
        // 查出数据库中的所有图片
        List<String> imgInDb = setMealService.findImg();
        // 7牛的-数据库的 imgIn7Niu剩下的就是要删除的
        imgIn7Niu.removeAll(imgInDb);
        // 把剩下的图片名转成数组
        String[] strings = imgIn7Niu.toArray(new String[]{});
        // 删除7牛上的垃圾图片
        QiNiuUtils.removeFiles(strings);
    }

}

