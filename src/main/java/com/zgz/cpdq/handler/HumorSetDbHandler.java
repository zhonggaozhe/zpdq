package com.zgz.cpdq.handler;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.zgz.cpdq.dao.IHumorDao;
import com.zgz.cpdq.pageVo.XhDqDeatilPageVo;
import com.zgz.cpdq.table.Humor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HumorSetDbHandler {

    @Autowired
    private IHumorDao iHumorDao;


    public void humorListHandler(List<XhDqDeatilPageVo> humorList){
        List<Humor> humors = new ArrayList<>();
        humorList.forEach(e ->{
            Humor humor = new Humor();
            humor.setContent(e.getContent());
//            humor.setWeight();
            humor.setDateTime(new Date());
            humor.setTitle(e.getTitle());
            humor.setUserId(RandomUtil.randomInt(1 , 3000));
            humor.setSource("笑话大全");
            humor.setThirdId(e.getId());
            humor.setCreateTime(new Date());
            humor.setUpdateTime(new Date());
            humor.setGoodNum(e.getGoodNum() == null ? 0 : Integer.valueOf(e.getGoodNum()));
            humor.setBadNum(e.getBadNum() == null ? 0 : Integer.valueOf(e.getBadNum()));
            humors.add(humor);
        });

        //入库
        iHumorDao.saveAll(humors);

    }


}
