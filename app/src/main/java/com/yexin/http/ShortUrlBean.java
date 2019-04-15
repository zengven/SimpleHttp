package com.yexin.http;

/**
 * author: zengven
 * date: 2019/4/4 17:33
 * desc: TODO
 */
public class ShortUrlBean {


    /**
     * Code : 0
     * ShortUrl : https://dwz.cn/GJrSif8f
     * LongUrl : http://www.baidu.com
     * ErrMsg :
     */

    public int Code;
    public String ShortUrl;
    public String LongUrl;
    public String ErrMsg;

    @Override
    public String toString() {
        return "ShortUrlBean{" +
                "Code=" + Code +
                ", ShortUrl='" + ShortUrl + '\'' +
                ", LongUrl='" + LongUrl + '\'' +
                ", ErrMsg='" + ErrMsg + '\'' +
                '}';
    }
}
