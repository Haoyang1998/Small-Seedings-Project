package com.seuchild.smallseedling.health;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.seuchild.smallseedling.R;

import java.util.ArrayList;

import devlight.io.library.ArcProgressStackView;
import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * Author: created by Ginger on 2018/9/3 14 26
 * E-Mail: 1020072294@qq.com
 */
public class HealthActivity extends AppCompatActivity{
    Context context;
    Toolbar toolbar;
    // 波浪进度条
    WaveLoadingView wavebar;
    // 首页环形进度条
    private ArcProgressStackView arcProgressStackView;

    private int MODEL_COUNT = 4;
    private int mCounter = 0;
    /**
     * The animation time in milliseconds that we take to display the steps taken
     */
    private static final int ANIMATION_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        context = HealthActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        arcProgressStackView = findViewById(R.id.healthpage_apsv);

        final String[] stringColors = getResources().getStringArray(R.array.devlight);
        final String[] stringBgColors = getResources().getStringArray(R.array.bg);

        final int[] colors = new int[MODEL_COUNT];
        final int[] bgColors = new int[MODEL_COUNT];
        for (int i = 0; i < MODEL_COUNT; i++) {
            colors[i] = Color.parseColor(stringColors[i]);
            bgColors[i] = Color.parseColor(stringBgColors[i]);
        }

        final ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();
        models.add(new ArcProgressStackView.Model("STRATEGY", 90, bgColors[0], colors[0]));
        models.add(new ArcProgressStackView.Model("DESIGN", 60, bgColors[1], colors[1]));
        models.add(new ArcProgressStackView.Model("DEVELOPMENT", 40, bgColors[2], colors[2]));
        models.add(new ArcProgressStackView.Model("QA", 80, bgColors[3], colors[3]));
        arcProgressStackView.setModels(models);
        // Start apsv animation on start
        arcProgressStackView.animateProgress();

        // 刷新
        arcProgressStackView.setAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                // Update goes here
                Log.d("onAnimationUpdate: ", String.valueOf(animation.getAnimatedValue()));
            }
        });

        arcProgressStackView.setAnimatorListener(new AnimatorListenerAdapter() {
            // 动画结束
            @Override
            public void onAnimationEnd(final Animator animation) {
                Toast.makeText(HealthActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
            }
        });

        // 点击事件
        arcProgressStackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                arcProgressStackView.animateProgress();
            }
        });


    }

    public void onBack(View v){
        finish();
    }

}
