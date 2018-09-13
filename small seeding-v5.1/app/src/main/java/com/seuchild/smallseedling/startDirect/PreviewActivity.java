package com.seuchild.smallseedling.startDirect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.seuchild.smallseedling.R;

import java.util.ArrayList;
import java.util.TimerTask;

public class PreviewActivity extends Activity {

    private ViewPager vpager_one;
    private ArrayList<View> aList;
    private MyPagerAdapter mAdapter;
    private Button startButton;
    private ImageView myFirst;
    private ImageView mySecond;
    private ImageView myThird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        vpager_one = (ViewPager) findViewById(R.id.vpager_one);
        myFirst = findViewById(R.id.mFirst);
        mySecond = findViewById(R.id.mSecond);
        myThird = findViewById(R.id.mThird);

        vpager_one.setOffscreenPageLimit(4);

        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        aList.add(li.inflate(R.layout.preview_vp_1, null, false));
        aList.add(li.inflate(R.layout.preview_vp_2, null, false));
        aList.add(li.inflate(R.layout.preview_vp_3, null, false));
        mAdapter = new MyPagerAdapter(aList);
        vpager_one.setAdapter(mAdapter);

        vpager_one.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        myFirst.setImageResource(R.drawable.preview_dot);
                        mySecond.setImageResource(R.drawable.preview_dotd);
                        myThird.setImageResource(R.drawable.preview_dotd);
                        break;
                    case 1:
                        myFirst.setImageResource(R.drawable.preview_dotd);
                        mySecond.setImageResource(R.drawable.preview_dot);
                        myThird.setImageResource(R.drawable.preview_dotd);
                        break;
                    case 2:
                        myFirst.setImageResource(R.drawable.preview_dotd);
                        mySecond.setImageResource(R.drawable.preview_dotd);
                        myThird.setImageResource(R.drawable.preview_dot);
                        startButton = (Button) findViewById(R.id.button_start);
                        startButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(PreviewActivity.this,InitialActivity.class);
                                startActivity(intent);
                            }
                        });

                        break;

                    default:
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public class MyTimetask extends TimerTask {

        @Override
        public void run() {
            PreviewActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (vpager_one.getCurrentItem()==0)
                        vpager_one.setCurrentItem(1);
                    else if(vpager_one.getCurrentItem()==1)
                        vpager_one.setCurrentItem(2);
                    else if(vpager_one.getCurrentItem()==2)
                        vpager_one.setCurrentItem(0);
                }
            });
        }

    }
}
