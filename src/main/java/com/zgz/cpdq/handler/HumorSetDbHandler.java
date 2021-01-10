package com.zgz.cpdq.handler;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.zgz.cpdq.constant.RedisKeyConstant;
import com.zgz.cpdq.dao.IHumorDao;
import com.zgz.cpdq.pageVo.qisongyike.QsykDetailsPageVo;
import com.zgz.cpdq.pageVo.xiaohuadaquan.XhDqDeatilPageVo;
import com.zgz.cpdq.redis.RedisUtil;
import com.zgz.cpdq.table.Humor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class HumorSetDbHandler {

    @Autowired
    private IHumorDao iHumorDao;

    @Autowired
    RedisUtil redisUtil;


    /**
     * 笑话大全数据入库操作
     *
     * @param humorList
     */
    public void humorListHandler(List<XhDqDeatilPageVo> humorList) {
        List<Humor> humors = new ArrayList<>();
        humorList.forEach(e -> {
            humors.add(this.convertXhdqToHumor(e));
        });
        //入库
        iHumorDao.saveAll(humors);
    }

//    /**
//     * 轻松一刻数据入库
//     */
//    public void qsykListHandler() {
//        long count = redisUtil.lGetListSize(RedisKeyConstant.qsyk_page_list_key);
//        if (count <= 0) {
//            log.info("轻松一刻数据入库缓存数据为空");
//        }
//
//        for (long i = 0; i < count; i++) {
//            Object object = redisUtil.lGetIndex(RedisKeyConstant.qsyk_page_list_key, i);
//            if (object == null) {
//                continue;
//            }
//            saveQsykData(object);
//            //入库成功从 redis 缓存中移除
//            //redisUtil.lRemove(RedisKeyConstant.qsyk_page_list_key , 1 , object);
//        }
//
//    }

    /**
     * 保存数据入库
     *
     * @param object
     */
    public void saveQsykData(Object object) {
        List<QsykDetailsPageVo> qsykDetailsPageVoList = JSONArray.parseArray(object.toString(), QsykDetailsPageVo.class);
        List<Humor> humorList = new ArrayList<>();
        qsykDetailsPageVoList.forEach(e -> {
            int num = iHumorDao.countByTitle(e.getTitle());
            if (num <= 0) {
                //数据不存在
                humorList.add(this.convertQsykToHumor(e));
            }
        });

        //批量入库
        if (humorList.size() > 0) {
            iHumorDao.saveAll(humorList);
        }
        log.info("轻松一刻数据入库 Size = {}", humorList.size());
    }

    /**
     * 轻松一刻对象转化成数据库实体
     *
     * @return
     */
    private Humor convertQsykToHumor(QsykDetailsPageVo pageVo) {
        Humor humor = new Humor();
        humor.setContent(pageVo.getContent());
        humor.setDateTime(new Date());
        humor.setTitle(pageVo.getTitle());
        humor.setUserId(RandomUtil.randomInt(1, 3000));
        humor.setSource("轻松一刻");
        humor.setThirdId(pageVo.getTitle().hashCode() + "");
        humor.setCreateTime(new Date());
        humor.setUpdateTime(new Date());
        humor.setGoodNum(0);
        humor.setBadNum(0);
        return humor;
    }

    /**
     * 笑话大全对象转化成数据库实体
     *
     * @return
     */
    private Humor convertXhdqToHumor(XhDqDeatilPageVo pageVo) {
        Humor humor = new Humor();
        humor.setContent(pageVo.getContent());
        humor.setDateTime(new Date());
        humor.setTitle(pageVo.getTitle());
        humor.setUserId(RandomUtil.randomInt(1, 3000));
        humor.setSource("笑话大全");
        humor.setThirdId(pageVo.getId());
        humor.setCreateTime(new Date());
        humor.setUpdateTime(new Date());
        humor.setGoodNum(pageVo.getGoodNum() == null ? 0 : Integer.valueOf(pageVo.getGoodNum()));
        humor.setBadNum(pageVo.getBadNum() == null ? 0 : Integer.valueOf(pageVo.getBadNum()));
        return humor;
    }

    /**
     * 笑话大全数据入库
     *
     * @param content
     */
    public void saveXhdqData(Object content) {
        List<XhDqDeatilPageVo> humorList = JSONArray.parseArray(content.toString() , XhDqDeatilPageVo.class);
        List<Humor> humors = new ArrayList<>();
        humorList.forEach(e -> {
            int num = iHumorDao.countByThirdId(e.getId());
            if (num <= 0) {
                //数据不存在
                humors.add(this.convertXhdqToHumor(e));
            }
        });
        //入库
        if (humors.size() > 0) {
            iHumorDao.saveAll(humors);
        }
        log.info("轻松一刻数据入库 Size = {}", humors.size());
    }
}
