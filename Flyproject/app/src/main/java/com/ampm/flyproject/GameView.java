package com.ampm.flyproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/17.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnTouchListener {
    private Bitmap twoChance;//二级缓存
    private Bitmap bg;
    private Bitmap my;
    private Bitmap diren;
    private Bitmap baozha;
    private Bitmap zidan;
    private SurfaceHolder holder;
    private ArrayList<GameImage> bitmaps = new ArrayList<>();
    private int display_width;
    private int display_height;
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        this.setOnTouchListener(this);
    }

    private void onTouchEvent(GameView gameView) {
    }

    private void init(){
        bg= BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        my=BitmapFactory.decodeResource(getResources(),R.drawable.my);
        diren=BitmapFactory.decodeResource(getResources(),R.drawable.diren);
        baozha=BitmapFactory.decodeResource(getResources(),R.drawable.baozha);
        zidan=BitmapFactory.decodeResource(getResources(),R.drawable.zidan);
        twoChance = Bitmap.createBitmap(display_width, display_height, Bitmap.Config.ARGB_8888);
        bitmaps.add(new BeijingImage(bg));
        bitmaps.add(new MyFeiji(my));
    }
    MyFeiji selectFeiji;
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            for (GameImage gameImage : bitmaps) {
                if(gameImage instanceof MyFeiji){
                    MyFeiji feiji = (MyFeiji)gameImage;
                    if(feiji.getX()<event.getX()
                            &&feiji.getY()<event.getY()
                            &&feiji.getX()+feiji.fWidth>event.getX()
                            &&feiji.getY()+feiji.fHeight>event.getY()){
                        selectFeiji =feiji;
                    }else {
                        selectFeiji =null;
                    }
                    break;
                }
            }
        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            if(selectFeiji!=null){
                selectFeiji.setX((int) event.getX()-selectFeiji.fWidth/2);
                selectFeiji.setY((int) event.getY()-selectFeiji.fHeight/2);
            }
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            selectFeiji=null;
        }
        return true;
    }

    private interface GameImage{
        public Bitmap getBitmap();
        public int getX();
        public int getY();

    }
    private class MyFeiji implements GameImage{
        private final Bitmap my;
        private ArrayList<Bitmap> bitmaps = new ArrayList();
        private int x;
        private int y;
        private int fWidth;
        private int fHeight;
        public MyFeiji(Bitmap my){
            this.my = my;
            bitmaps.add(Bitmap.createBitmap(my,0,0,my.getWidth()/4,my.getHeight()));
            bitmaps.add(Bitmap.createBitmap(my,(my.getWidth()/4)*1,0,my.getWidth()/4,my.getHeight()));
            bitmaps.add(Bitmap.createBitmap(my,(my.getWidth()/4)*2,0,my.getWidth()/4,my.getHeight()));
            bitmaps.add(Bitmap.createBitmap(my,(my.getWidth()/4)*3,0,my.getWidth()/4,my.getHeight()));
            fHeight=my.getHeight();
            fWidth = my.getWidth()/4;
            x=(display_width-my.getWidth()/4)/2;
            y=display_height-my.getHeight()-10;
        }

        public int getfHeight() {
            return fHeight;
        }

        public int getfWidth() {
            return fWidth;
        }

        int index=0;
        @Override
        public Bitmap getBitmap() {
              Bitmap bitmap = bitmaps.get(index);
              index++;
            if(index==bitmaps.size()){
                index=0;
            }
            return bitmap;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }
    }
    private class BeijingImage implements GameImage {
        private final Bitmap bg;
        private final Bitmap newBitmap;
        private int scrollHeight = 0;
        private BeijingImage(Bitmap bg){
            this.bg = bg;
            newBitmap = Bitmap.createBitmap(display_width,display_height, Bitmap.Config.ARGB_8888);
        }
        @Override
        public Bitmap getBitmap(){
            Paint paint = new Paint();
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawBitmap(bg,new Rect(0,0,bg.getWidth(),bg.getHeight()),
                    new Rect(0,scrollHeight,display_width,display_height+scrollHeight),paint);
            canvas.drawBitmap(bg,new Rect(0,0,bg.getWidth(),bg.getHeight()),
                    new Rect(0,-display_height+scrollHeight,display_width,scrollHeight),paint);
            scrollHeight++;
            if(scrollHeight==display_height){
                scrollHeight=0;
            }
            return newBitmap;
        }

        @Override
        public int getX() {
            return 0;
        }

        @Override
        public int getY() {
            return 0;
        }

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        this.holder = holder;
//        runstate = true;
//        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        display_width= width;
        display_height = height;
        this.holder = holder;
        init();
        runstate = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        runstate = false;
    }

    boolean runstate = false;

    public void run() {
        Paint paint = new Paint();
//        Random random = new Random();
//        paint.setColor(Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        while (runstate) {
            try {
                //获得绘画
                Canvas twocanvas = new Canvas(twoChance);
                for(GameImage bitmapbg : bitmaps){
                    twocanvas.drawBitmap(bitmapbg.getBitmap(),bitmapbg.getX(),bitmapbg.getY(),paint);
                }
                Canvas canvas = holder.lockCanvas();
                canvas.drawBitmap(twoChance,0,0,new Paint());
                holder.unlockCanvasAndPost(canvas);
                Thread.sleep(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
