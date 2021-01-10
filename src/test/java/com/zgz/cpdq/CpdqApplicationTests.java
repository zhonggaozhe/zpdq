package com.zgz.cpdq;

import com.zgz.cpdq.handler.MenuSetDbHandler;
import com.zgz.cpdq.service.spider.SpiderCpdqService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CpdqApplicationTests {


    @Autowired
    SpiderCpdqService spiderCpdqService;

    @Autowired
    MenuSetDbHandler menuSetDbHandler;


    /**
     * 第一步: 拉取大分类到 Redis
     */
    @Test
    void menuCategory() {
//		spiderCpdqService.menuCategory();
    }


    /**
     * 第二步: 解析分类入库
     */
    @Test
    @Before("menuCategory")
    void initCategory() {
        //menuSetDbHandler.categoryDataHandler();
    }


    /**
     * 第二步: 解析菜品分类入库
     */
    @Before("initCategory")
    @Test
    void initMenuCateGory() {
//		menuSetDbHandler.originalMenuCateGoryHandler();
    }

    /**
     * 第三步: 拉取菜单数据到 Redis
     */
    @Test
    void crawlMenuData() {
//		spiderCpdqService.menu();
    }

    /**
     * 第四步: 解析菜单数据,在 Controller 里面
     */
    @Test
    void initMenuCateGoryHandler() {
    }

    /**
     * 第五步: 拉取单品菜详情
     */
    @Test
    void pullMenuDetails() {
        menuSetDbHandler.pullMenuDetails();
    }


}
