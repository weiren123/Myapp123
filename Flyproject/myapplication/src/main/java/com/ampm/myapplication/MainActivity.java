package com.ampm.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout ll_view;
    private int count=8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        ll_view = (LinearLayout) findViewById(R.id.ll_view);
        if(count==8){
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
            for(int i = 0;i<3;i++){
                LinearLayout linearLayout = new LinearLayout(this);
                ll_view.addView(linearLayout);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout ll_1 = (LinearLayout) ll_view.getChildAt(0);
            ll_1.setGravity(Gravity.CENTER);
            CustomView view = new CustomView(this);
            view.setline2(110);
            ll_1.addView(view,params);
            LinearLayout ll_2 = (LinearLayout) ll_view.getChildAt(1);
            ll_2.setGravity(Gravity.CENTER);
            ll_2.setLayoutParams(params1);
            for (int i=0;i<2;i++){
                CustomView customView = new CustomView(this);
                customView.setline2(40);
                ll_2.addView(customView,params);
            }
            for(int i = 0; i < 2; i++) {
                CustomView view1 = (CustomView) ll_2.getChildAt(i);
                params.setMargins(300,0,0,0);
                view1.setLayoutParams(params);
            }
            LinearLayout ll_3 = (LinearLayout) ll_view.getChildAt(2);
            ll_3.setGravity(Gravity.CENTER);
            ll_3.setLayoutParams(params1);
            for (int i=0;i<4;i++){
                CustomView customView = new CustomView(this);
                customView.setline2(40);
                ll_3.addView(customView);
            }
            for(int i = 0; i < 4; i++) {
                CustomView view3 = (CustomView) ll_3.getChildAt(i);
                params1.setMargins(50,0,0,0);
                view3.setLayoutParams(params1);
            }
        }
    }
}
