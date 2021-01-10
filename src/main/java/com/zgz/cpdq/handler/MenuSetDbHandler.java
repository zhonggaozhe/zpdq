package com.zgz.cpdq.handler;

import cn.hutool.core.util.PageUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;
import com.zgz.cpdq.dao.ICategoryDao;
import com.zgz.cpdq.dao.IMenuCategoryDao;
import com.zgz.cpdq.dao.IMenuDataDao;
import com.zgz.cpdq.pageVo.cpdq.CategoryPageVo;
import com.zgz.cpdq.pageVo.cpdq.MenuDetailsPageVo;
import com.zgz.cpdq.redis.RedisUtil;
import com.zgz.cpdq.table.Category;
import com.zgz.cpdq.table.MenuCateGory;
import com.zgz.cpdq.table.MenuData;
import com.zgz.cpdq.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class MenuSetDbHandler {


    @Autowired
    RedisUtil redisUtil;

    @Autowired
    IMenuDataDao iMenuDataDao;

    @Autowired
    ICategoryDao iCategoryDao;

    @Autowired
    IMenuCategoryDao iMenuCategoryDao;


    public void originalMenuCateGoryHandler() {

        Object obj = redisUtil.get("original");
        if (null == obj) {
            log.error("原始数据在 redis 中不存在.....................");
            return;
        }

        final List<MenuCateGory> menuDataList = new ArrayList<>();
        CategoryPageVo categoryPageVo = JSONObject.parseObject(obj.toString(), CategoryPageVo.class);
        categoryPageVo.getA_html().stream().forEach(htmlStr -> {
            log.info("源数据:{}", htmlStr);
            Document document = Jsoup.parse(htmlStr);
            final String name = document.select("body > a").html();
            final String href = document.select("body > a").attr("href");
            Long id = Long.valueOf(href.split("/")[2]);
            log.info("解析后数据 , name = {}, href = {}", name, href);
            final Date now = new Date();
            MenuCateGory menuCateGory = new MenuCateGory();
            menuCateGory.setName(name);
            menuCateGory.setUrl("https://www.xinshipu.com".concat(href));
            menuCateGory.setCategoryId(0l);
            menuCateGory.setRemark("");
            menuCateGory.setPartiesId(id);
            menuCateGory.setSource("心食谱");
            menuCateGory.setCreateTime(now);
            menuCateGory.setUpdateTime(now);
            menuDataList.add(menuCateGory);
        });
        iMenuCategoryDao.saveAll(menuDataList);
    }


    public void categoryDataHandler() {
        Object obj = redisUtil.get("original");
        if (null == obj) {
            log.error("原始数据在 redis 中不存在.....................");
            return;
        }
        final List<Category> categoryList = new ArrayList<>();
        CategoryPageVo categoryPageVo = JSONObject.parseObject(obj.toString(), CategoryPageVo.class);
        List<String> list = categoryPageVo.getLables();
        for (int i = 0; i < list.size(); i++) {
            String lable = list.get(i);
            final Date now = new Date();
            Category category = new Category();
            category.setParentId(0L);
            category.setLable(lable);
            category.setSort(i);
            category.setId(0L);
            category.setCreateTime(new Date());
            category.setUpdateTime(new Date());
            categoryList.add(category);
        }
        iCategoryDao.saveAll(categoryList);
    }

    final int pageSize = 10;

    public void originalMenuDataHandler() {
        List<MenuCateGory> menuCateGoryList = iMenuCategoryDao.findAll();
        if (null == menuCateGoryList) {
            log.error("数据库查询菜单品类数据失败!");
            return;
        }
        int count = menuCateGoryList.size();
        int totalPage = PageUtil.totalPage(count, pageSize);
        for (int i = 0; i < totalPage; i++) {
            //获取当前页数据
            int rows[] = PageUtil.transToStartEnd(i, pageSize);
            List<MenuCateGory> menuCateGories = menuCateGoryList.subList(rows[0], rows[1] >= count ? count : rows[1]);
//            log.info("当前页={} , 数据大小={}", i + 1, menuCateGories.size());
            ThreadPoolUtil.getPool().execute(new MenuThread(menuCateGories));
        }

    }


    /**
     * 解析单个 DIV 模块属性
     *
     * @param divHtml
     */
    public MenuData parseDivElement(String divHtml) {
        Document document = Jsoup.parse(divHtml);
        Elements a_element = document.select("body > div > a");
        String href = a_element.attr("href");
        String title = a_element.attr("title");

        Elements img_element = a_element.select("div > img");
        String src = img_element.attr("src");
        String tabindex = img_element.attr("tabindex");

        MenuData menuData = new MenuData();
        menuData.setTitle(title);
        menuData.setHref("https://www.xinshipu.com" + href);
        menuData.setImageurl("https:" + src);
        menuData.setSort(Integer.valueOf(tabindex));
        menuData.setCreateTime(new Date());
        menuData.setUpdateTime(new Date());
        log.info("解析单个DIV模块属性 , {}", menuData.toString());

        return menuData;
    }

    final int pageSize_details = 20;


    public void pullMenuDetails() {
        List<MenuData> menuDataList = iMenuDataDao.findAll();
        int count = menuDataList.size();
        int totalPage = PageUtil.totalPage(count, pageSize_details);

        for (int i = 0; i < 1; i++) {
            //获取当前页数据
            int rows[] = PageUtil.transToStartEnd(i, pageSize_details);
            List<MenuData> list = menuDataList.subList(rows[0], rows[1] >= count ? count : rows[1]);
//            ThreadPoolUtil.getPool().execute(new MenuThread(menuCateGories));
            crawlerMenuDetails(list);
        }

    }


    public void crawlerMenuDetails(List<MenuData> list){
        List<String> urls = new ArrayList<>();
        list.stream().forEach(menuData -> {urls.add(menuData.getHref());});
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls(urls.toArray(new String[urls.size()]))
                .setAllowSpread(false)
                .setThreadCount(1)
                .setPauseMillis(5 * 1000)
                .setPageParser(new PageParser<MenuDetailsPageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, MenuDetailsPageVo pageVo) {
                        // 解析封装 PageVo 对象
                        redisUtil.set("details_".concat(pageVo.getMenuId()), pageVo);
                    }
                })
                .build();
        crawler.start(true);

    }



    class MenuThread implements Runnable {

        private List<MenuCateGory> menuCateGories;

        public MenuThread(List<MenuCateGory> menuCateGories) {
            this.menuCateGories = menuCateGories;
        }

        @Override
        public void run() {
            log.info("当前线程-{} , ID-{} , 处理数据大小: {}", Thread.currentThread().getName(), Thread.currentThread().getId(), menuCateGories.size());
            List<MenuData> menuDataList = new ArrayList<>();
            for (MenuCateGory menuCateGory : menuCateGories) {
                String key = "meun_" + menuCateGory.getId();
                Object obj = redisUtil.sGet(key);
                if (obj == null) {
                    log.error("当前线程-{} , ID-{} , 菜品-{}({}) 从缓存中获取数据失败!", Thread.currentThread().getName(),
                            Thread.currentThread().getId(), menuCateGory.getName(), menuCateGory.getId());
                    continue;
                }

                Long id = menuCateGory.getId();
                LinkedHashSet<JSONObject> linkedHashSet = (LinkedHashSet) obj;
                for (JSONObject jsonObject : linkedHashSet){
                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("menus"));
                    jsonArray.forEach(array -> {
                        MenuData menuData = parseDivElement((String) array);
                        menuData.setMenuCategoryId(id);
                        menuDataList.add(menuData);
                    });
                }
            }
            //入库操作
            if(menuDataList.size() > 0){
                log.info("当前线程-{} , ID-{} , 入库操作 -----$$$$$$$$$$$$-------处理数据大小: {}", Thread.currentThread().getName(), Thread.currentThread().getId(), menuDataList.size());
                iMenuDataDao.saveAll(menuDataList);
            }
        }
    }

}

