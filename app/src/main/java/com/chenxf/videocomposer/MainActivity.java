package com.chenxf.videocomposer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 将2个视频文件，拼接为一个文件，拼接前提是，2个文件的编码信息一样，包括video codec(H264的话，包括profile/level)
 * video width/height, audio codec, audio sample rate, channel number
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void startCompose() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList videoList = new ArrayList<>();
                //待合成的2个视频文件
                videoList.add("/storage/emulated/0/DCIM/test1.mp4");
                videoList.add("/storage/emulated/0/DCIM/test2.mp4");
                VideoComposer composer = new VideoComposer(videoList, "/storage/emulated/0/DCIM/out.mp4");
                final boolean result = composer.joinVideo();

                mButton.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "合成结果 " + result, Toast.LENGTH_LONG);
                    }
                });
                Log.i(TAG, "compose result: " + result);
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button) {
            startCompose();
        }
    }
}
