package com.seuchild.smallseedling.education;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.seuchild.smallseedling.R;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Author: created by Ginger on 2018/9/12 14 19
 * E-Mail: 1020072294@qq.com
 */
public class EducationVideoActivity extends AppCompatActivity {
    // 视频播放控件
    StandardGSYVideoPlayer videoPlayer;
    OrientationUtils orientationUtils;

    // 视屏相关
    private String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private String title = "视频标题";
    private String image_url = "http://39.105.38.48/images/2018/09/12/eyes.gif"; //视频结束图片url

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_educationvideo);
        // 获取activity传递的参数
        Intent intent=getIntent();
        url = intent.getStringExtra("video_url");
        title = intent.getStringExtra("video_title");
        image_url = intent.getStringExtra("video_image");
        init();
    }

    private void init() {
        videoPlayer =  (StandardGSYVideoPlayer)findViewById(R.id.video_player);

        videoPlayer.setUp(url, true, title);

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imageView.setImageResource(R.mipmap.xxx1);
        Glide.with(this).load(image_url).into(imageView);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

}
