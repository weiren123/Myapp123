package com.ampm.flyproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.tencent.tinker.lib.tinker.TinkerInstaller;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private TabLayout main_tab;
    private ViewPager main_viewpager;
    private String url = "http://news.sina.com.cn";
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> names1 = new ArrayList<>();
    private ArrayList<View> mViewList = new ArrayList<>();
    private ArrayList<String> hrefUrl = new ArrayList<>();
    private WebView webview;
    private View view;
    private Button btn_fix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(new GameView(this));
//        init();
        initView();
    }

    private void initView() {
        main_tab = (TabLayout) findViewById(R.id.main_tab);
        main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
        btn_fix = (Button) findViewById(R.id.btn_fix);
        btn_fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"patch_signed_7zip.apk";
                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),path);
            }
        });
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection conn = Jsoup.connect(url);
                // 修改http包中的header,伪装成浏览器进行抓取
                conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
                Document doc = null;

                try {
                    doc = conn.get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements elements1 = doc.getElementsByClass("main-nav");
                Elements select = elements1.select("li").select("a");
                for (int i = 0; i < select.size(); i++) {
                    String href = select.get(i).attr("href");
                    hrefUrl.add(href);
                    Log.e("APP-------------TAG", href);
                }
                String[] strings = null;
                Elements li = null;
                for (int i = 0; i < elements1.size(); i++) {
                    String li1 = elements1.get(i).select("li").text();
                    li = elements1.select("li");
                    names.add(li1);
                    Log.e("APP_TAG", select.html());
                }

                String s = names.get(0);
                String[] split = s.split(" ");
                for (int i = 0; i < split.length; i++) {
                    names1.add(split[i]);
                }
                for (int i = 0; i < names1.size(); i++) {
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < mViewList.size(); i++) {
                            view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main_item, null);
                            mViewList.add(view);
                            webview = (WebView) view.findViewById(R.id.webviewid);
                            webview.loadUrl(hrefUrl.get(i));
                        }
                        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
                        main_viewpager.setAdapter(mAdapter);//给ViewPager设置适配器
                        main_tab.setupWithViewPager(main_viewpager);//将TabLayout和ViewPager关联起来
                        main_tab.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为系统默认模式
                        for (int i = 0; i < names1.size(); i++) {

//                            main_tab.addTab(main_tab.newTab().setText(names1.get(i)));//添加tab选项卡
                        }
                    }
                });
            }
        }).start();


    }

    public class MyPagerAdapter extends PagerAdapter {
        private final List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return names1.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return names1.get(position);//页卡标题
        }

    }
}
