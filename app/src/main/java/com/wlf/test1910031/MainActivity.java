package com.wlf.test1910031;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mShowTv;
    private Handler mChildHandler;
    private Handler mUiHandler;
    private HandlerThread mHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowTv = findViewById(R.id.tv_show);
        mUiHandler = new Handler();

        // 1.创建 HandlerThread 的示例对象
        mHandlerThread = new HandlerThread("testHandlerThread");

        // 2.启动 HandlerThread 子线程
        mHandlerThread.start();

        // 3.创建绑定子线程 Looper 的 Handler 对象
        mChildHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                try {
                    int what = msg.what;
                    switch (what) {
                        case 1:
                            // 模拟耗时操作
                            Thread.sleep(1000);
                            mUiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mShowTv.setText("Show Your Image");
                                }
                            });
                            break;
                        case 2:
                            Thread.sleep(1000);
                            mUiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mShowTv.setText("Show Your Audio");
                                }
                            });
                            break;
                        case 3:
                            Thread.sleep(1000);
                            mUiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mShowTv.setText("Show Your Video");
                                }
                            });
                        default:
                            break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    // 4.发送消息给子线程
    public void downloadImage(View view) {
        Message firstMessage = Message.obtain();
        firstMessage.what = 1;
        firstMessage.obj = "DownLoad Image";
        mChildHandler.sendMessage(firstMessage);
    }

    public void downloadAudio(View view) {
        Message secondMessage = Message.obtain();
        secondMessage.what = 2;
        secondMessage.obj = "DownLoad Audio";
        mChildHandler.sendMessage(secondMessage);
    }

    public void downloadVideo(View view) {
        Message thirdMessage = Message.obtain();
        thirdMessage.what = 3;
        thirdMessage.obj = "DownLoad Video";
        mChildHandler.sendMessage(thirdMessage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 5.结束 Looper 消息循环
        mHandlerThread.quit();
    }
}
