package com.ampm.flyproject;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Administrator on 2017/5/20.
 */

public class TestHtml {
    private static String url = "http://www.toutiao.com";
    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = Jsoup.connect(url);
                // 修改http包中的header,伪装成浏览器进行抓取
                conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
                Document doc =null;

                try {
                    doc = conn.get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements elementsByClass = doc.getElementsByClass("wchannel-item");
                for(int i = 0; i < elementsByClass.size(); i++) {

                    String html = doc.html();
                    Log.e("11111111111",html);
                }
            }
        }).start();
    }
}
