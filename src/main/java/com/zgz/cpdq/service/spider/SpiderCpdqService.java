package com.zgz.cpdq.service.spider;

import cn.hutool.json.JSONUtil;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;
import com.xuxueli.crawler.util.RegexUtil;
import com.zgz.cpdq.dao.IMenuCategoryDao;
import com.zgz.cpdq.dao.IMenuDataDao;
import com.zgz.cpdq.pageVo.cpdq.CategoryPageVo;
import com.zgz.cpdq.pageVo.cpdq.MenuPageVo;
import com.zgz.cpdq.redis.RedisUtil;
import com.zgz.cpdq.table.MenuCateGory;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpiderCpdqService {


    @Autowired
    RedisUtil redisUtil;

    @Autowired
    IMenuCategoryDao iMenuCategoryDao;

    @Autowired
    IMenuDataDao iMenuDataDao;

    public void menuCategory() {
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("https://www.xinshipu.com/%E8%8F%9C%E8%B0%B1%E5%A4%A7%E5%85%A8.html")
                .setAllowSpread(false)
                .setThreadCount(1)
//                .setProxyMaker(new RandomProxyMaker().addProxy(proxy))
                .setPageParser(new PageParser<CategoryPageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, CategoryPageVo pageVo) {
                        // 解析封装 PageVo 对象
                        log.info("getSinaAlerts news : {} ", JSONUtil.toJsonStr(pageVo));
                        redisUtil.set("original", pageVo);
                    }
                })
                .build();
        crawler.start(true);
    }

    public void menu() {

        List<MenuCateGory> menuCateGoryList = iMenuCategoryDao.findAll();
        if (null == menuCateGoryList) {
            log.error("数据库查询菜单品类数据失败!");
            return;
        }

        menuCateGoryList.stream().parallel().forEach(menuCateGory -> {
            gg(menuCateGory.getId(), menuCateGory.getUrl());
        });


    }



    private void gg(Long id, String... url) {
        String whiteRegex="https://www.xinshipu.com/"+"[\\\\da-zA-z]+"+"/"+"\\d+"+"/"+"\\?"+"page="+"\\d+";
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls(url)
                .setWhiteUrlRegexs(whiteRegex)
                .setAllowSpread(true)
                .setThreadCount(1)
                .setPauseMillis(5 * 1000)
                .setPageParser(new PageParser<MenuPageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, MenuPageVo pageVo) {
                        // 解析封装 PageVo 对象
                        redisUtil.sSet("meun_".concat(String.valueOf(id)), pageVo);
                    }
                })
                .build();
        crawler.start(true);
    }

    public static void main(String[] args) {
        //String whiteRegex = "https://www\\.xinshipu\\.com/\\\\w\\/1502/\\?page=\\b[1-6]";
//        String whiteRegex = "";
        String whiteRegex="https://www.xinshipu.com/"+"[\\\\da-zA-z]+"+"/"+"\\d+"+"/"+"\\?"+"page="+"\\d+";
        String link = "https://www.xinshipu.com/jiachangzuofa/2346/?page=6";
        System.out.println(RegexUtil.matches(whiteRegex, link));


        String whiteRegex1 = "http://finance\\.sina\\.com\\.cn/roll/index\\.d\\.html\\?cid=56975&page=\\b[1-2]";
        String link1 = "http://finance.sina.com.cn/roll/index.d.html?cid=56975&page=1";
        System.out.println(RegexUtil.matches(whiteRegex1, link1));

        try{
            Class child_class = Class.forName("com.zgz.cpdq.pageVo.cpdq.MaterialPageVo");
            Object object = child_class.newInstance();
        }catch (Exception e){

        }


        String whiteRegex3 = "https://duanziwang.com/category/%E7%BB%8F%E5%85%B8%E6%AE%B5%E5%AD%90/"+"\\d+"+"/index.html";
        String link3 = "https://duanziwang.com/category/%E7%BB%8F%E5%85%B8%E6%AE%B5%E5%AD%90/2/index.html";
        System.out.println(RegexUtil.matches(whiteRegex3, link3));

        String url4 = "http://xiaohua.zol.com.cn/chaoji/1.html";

        String whiteRegex4 = "http://xiaohua.zol.com.cn/chaoji/"+"\\d"+".html";
        System.out.println(RegexUtil.matches(whiteRegex4, url4));

    }


}
