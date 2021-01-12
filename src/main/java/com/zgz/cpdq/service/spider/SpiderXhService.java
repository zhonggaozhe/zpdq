package com.zgz.cpdq.service.spider;

import cn.hutool.json.JSONUtil;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.parser.PageParser;
import com.zgz.cpdq.constant.RedisKeyConstant;
import com.zgz.cpdq.entity.CrawlUrl;
import com.zgz.cpdq.enums.MessageTypeEnums;
import com.zgz.cpdq.handler.HumorSetDbHandler;
import com.zgz.cpdq.pageVo.biedoule.BdlPageVo;
import com.zgz.cpdq.pageVo.qisongyike.QsykPageVo;
import com.zgz.cpdq.pageVo.xiaohuadaquan.XhDqPageVo;
import com.zgz.cpdq.pageVo.xiaohuadaquan.XmPageVo;
import com.zgz.cpdq.redis.MessageEntity;
import com.zgz.cpdq.redis.MessageProducer;
import com.zgz.cpdq.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpiderXhService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    HumorSetDbHandler humorSetDbHandler;

    @Autowired
    private MessageProducer producer;


    /**
     * 百度网站笑话集锦
     */
    public void getHumor() {
        String whiteRegex3 = "https://duanziwang.com/category/%E7%BB%8F%E5%85%B8%E6%AE%B5%E5%AD%90/" + "\\d+" + "/index.html";
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls("https://duanziwang.com/category/%E7%BB%8F%E5%85%B8%E6%AE%B5%E5%AD%90/index.html")
                .setWhiteUrlRegexs(whiteRegex3)
                .setAllowSpread(true)
                .setThreadCount(8)
                .setPageParser(new PageParser<XmPageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, XmPageVo pageVo) {
                        // 解析封装 PageVo 对象
                        log.info("幽默笑话: {} ", JSONUtil.toJsonStr(pageVo));
                        redisUtil.sSet("xmList", pageVo.getXmList());
                    }
                })
                .build();
        crawler.start(true);
    }


    /**
     * 笑话大全
     * url http://xiaohua.zol.com.cn/neihan
     * @param crawlUrl
     */
    public void getXhDaQuan(CrawlUrl crawlUrl) {
//        String url = "http://xiaohua.zol.com.cn/chaoji/1.html";
//        String whiteRegex4 = "http://xiaohua.zol.com.cn/chaoji/"+"\\d"+".html";
//        String url = "http://xiaohua.zol.com.cn/neihan/1.html";
//        String whiteRegex4 = "http://xiaohua.zol.com.cn/neihan/" + "\\d" + ".html";

        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls(crawlUrl.getUrls())
                .setWhiteUrlRegexs(crawlUrl.getWhiteRegex())
                .setAllowSpread(crawlUrl.isAllowSpread())
                .setThreadCount(2)
                .setPauseMillis(1000)
                .setFailRetryCount(2)
                .setPageParser(new PageParser<XhDqPageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, XhDqPageVo pageVo) {
                        // 解析封装 PageVo 对象
                        log.info("笑话大全拉取数据: {} ", JSONUtil.toJsonStr(pageVo));
                        producer.sendMeassage(new MessageEntity(RedisKeyConstant.qsyk_page_list_key, MessageTypeEnums.XHDQ, pageVo.getXmList()));
                    }
                })
                .build();
        crawler.start(true);
    }

    /**
     * 轻松一刻
     * http://www.17989.com/xiaohua/duanxiaohua/
     * @param urls
     */
    public void getQsyk(CrawlUrl urls) {
//        String whiteRegex[] = {"http://www.17989.com/xiaohua/duanxiaohua/"
//                , "http://www.17989.com/xiaohua/duanxiaohua/" + "\\d" + ".htm",};
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls(urls.getUrls())
                .setWhiteUrlRegexs(urls.getWhiteRegex())
                .setAllowSpread(urls.isAllowSpread())
                .setThreadCount(2)
                .setPauseMillis(1000)
                .setFailRetryCount(2)
                .setPageParser(new PageParser<QsykPageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, QsykPageVo pageVo) {
                        // 解析封装 PageVo 对象
                        log.info("轻松一刻拉取数据: {} ", JSONUtil.toJsonStr(pageVo));
                        producer.sendMeassage(new MessageEntity(RedisKeyConstant.qsyk_page_list_key, MessageTypeEnums.QSYK, pageVo.getQsykDetailsPageVos()));
                    }
                })
                .build();
        crawler.start(true);
    }

    public static void main(String[] args) {
        String whiteRegex4 = "http://xiaohua.zol.com.cn/chaoji/"+"\\d"+".html";
        System.out.println(whiteRegex4);
        System.out.println("http://xiaohua.zol.com.cn/neihan/\\d.html");
        System.out.println("http://www.17989.com/xiaohua/duanxiaohua/\\d.htm");
        System.out.println("http://www.17989.com/xiaohua/duanxiaohua/" + "\\d" + ".htm");
    }

    /**
     * 别逗了
     * url: https://www.biedoul.com/t/5pCe56yR5q615a2Q.html
     * @param crawlUrl
     */
    public void getBiedoule(CrawlUrl crawlUrl) {
        XxlCrawler crawler = new XxlCrawler.Builder()
                .setUrls(crawlUrl.getUrls())
                .setWhiteUrlRegexs(crawlUrl.getWhiteRegex())
                .setAllowSpread(crawlUrl.isAllowSpread())
                .setThreadCount(1)
                .setPauseMillis(1000)
                .setFailRetryCount(2)
                .setPageParser(new PageParser<BdlPageVo>() {
                    @Override
                    public void parse(Document html, Element pageVoElement, BdlPageVo pageVo) {
                        // 解析封装 PageVo 对象
                        log.info("别逗了取数据: {} ", JSONUtil.toJsonStr(pageVo));
                        producer.sendMeassage(new MessageEntity(RedisKeyConstant.qsyk_page_list_key, MessageTypeEnums.BDL, pageVo.getBdlDetailsPageVoList()));
                    }
                })
                .build();
        crawler.start(true);
    }
}
