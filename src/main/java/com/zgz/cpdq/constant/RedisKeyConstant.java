package com.zgz.cpdq.constant;

/**
 * redis 缓存 Keys
 */
public class RedisKeyConstant {


    /**
     * 笑话大全原始数据储存 Key
     */
    public final static String xhdq_page_list_key = "xhdq_page_list_key";

    /**
     * 轻松一刻原始数据储存 Key
     */
    public final static String qsyk_page_list_key = "qsyk_page_list_key";

    /**
     * 首页列表分页缓存
     */
    public final static String getXdData_page = "getXdData_page_map";

    /**
     * 抓取数据URL 缓存 Key
     */
    public final static String crawl_cache_urls = "crawl_cache_urls";

    /**
     * 抓取数据URL定时任务锁
     */
    public final static String crawl_cache_urls_lock_key = "crawl_cache_urls_lock_key";


}
