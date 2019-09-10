package com.lanqiao.circle.util;

/**
 * @author 刘佳昇
 * @Date 2019/8/23 14:36
 */

public class PageCheck {
    public static Integer checkLimit( Integer limit) {
        limit =limit<=0 ?10:limit;
        return limit;
    }
    public  static Integer checkPage(Integer page){
        page = page<=0 ? 1:page;
        return page;
    }

    public static int calculateStart(int page, int limit){
        limit = limit <= 0 ? 10 : limit;
        return page > 0 ? (page - 1) * limit : 0;
    }

    public static int calculateStart(int page, int limit, int count) {
        limit = limit <= 0 ? 10 : limit;

        if(((page-1)*limit + limit)<count) {
            return (page - 1) * limit;
        }
        return page > 0 ? (page - 1) * limit : 0;
    }
}
