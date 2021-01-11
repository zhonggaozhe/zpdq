package com.zgz.cpdq.controller;

import cn.hutool.core.date.DateUtil;
import com.zgz.cpdq.HumorVo;
import com.zgz.cpdq.constant.RedisKeyConstant;
import com.zgz.cpdq.dao.IHumorDao;
import com.zgz.cpdq.entity.CrawlUrl;
import com.zgz.cpdq.redis.RedisUtil;
import com.zgz.cpdq.service.UserService;
import com.zgz.cpdq.table.Humor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HumorController {

    @Autowired
    private IHumorDao iHumorDao;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 添加抓取 URL
     *
     * @param crawlUrl
     * @return
     */
    @RequestMapping(value = "addCrawlUrl", method = RequestMethod.POST)
    public Object addCrawlUrl(@Valid @RequestBody CrawlUrl crawlUrl) {
        Map<String, Object> result = new HashMap<>();
        result.put("statusCode", 200);
        result.put("data", redisUtil.sSet(RedisKeyConstant.crawl_cache_urls, crawlUrl));
        return result;
    }


    /**
     * 精选数据列表
     *
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "getXdData")
    public Object getXdData(int pageNum) {
        Map<String, Object> result = new HashMap<>();
        result.put("statusCode", 200);
        Object object = redisUtil.hget(RedisKeyConstant.getXdData_page, pageNum + "");
        if (object != null) {
            result.put("data", object);
            return result;
        }
        List<HumorVo> humorVoList = getHumorVos(pageNum);
        result.put("data", humorVoList);
        return result;
    }

    private List<HumorVo> getHumorVos(int pageNum) {
        PageRequest pageable = PageRequest.of(pageNum - 1, 6, Sort.by(Sort.Direction.DESC, "id"));
        Page<Humor> humorPage = iHumorDao.findAll(pageable);
        List<HumorVo> humorVoList = new ArrayList<>();
        humorPage.get().forEach(e -> {
            HumorVo humorVo = new HumorVo();
            humorVo.setUserInfo(userService.getUserById(e.getUserId()));
            humorVo.setTitle(e.getTitle());
            humorVo.setContent(e.getContent());
            humorVo.setDateTime(DateUtil.formatDateTime(e.getDateTime()));
            humorVoList.add(humorVo);
        });

        if (humorVoList.size() > 0) {
            redisUtil.hset(RedisKeyConstant.getXdData_page, pageNum + "", humorVoList);
        }
        return humorVoList;
    }
}
