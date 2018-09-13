package com.seuchild.smallseedling.parent;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.SupportMapFragment;

import java.io.File;

import com.baidu.mapapi.model.LatLng;
import com.seuchild.smallseedling.StartActivity;
import com.seuchild.smallseedling.calendar.CalendarActivity;
import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.myInfo.MyInfoActivity;
import com.seuchild.smallseedling.square.SquareActivity;


public class ParentActivity extends AppCompatActivity {

    SupportMapFragment mapViewD,mapViewM;
    BaiduMap mBaiduMapD,mBaiduMapM;
    private static final String TAG = "ParentActivity";

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private CircleImageView iv_dad;
    private CircleImageView iv_mom;
    private static int count;
    private Toolbar mToolbar;
    private BottomNavigationView parent_bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_parent);

        parent_bnv = findViewById(R.id.parent_bnv);
        LatLng ll = new LatLng(35.447358,119.573984);
        MapStatusUpdate u1 = MapStatusUpdateFactory.newLatLngZoom(ll,18.0f);
        mapViewM = (SupportMapFragment)(getSupportFragmentManager().findFragmentById(R.id.mom_frag));
        mBaiduMapM = mapViewM.getBaiduMap();
        mBaiduMapM.setMapStatus(u1);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_marke);
        OverlayOptions overlayOptions = new MarkerOptions().position(ll).icon(bitmapDescriptor);
        mBaiduMapM.addOverlay(overlayOptions);

        LatLng l = new LatLng(32.078431,118.782675);
        MapStatusUpdate u2 = MapStatusUpdateFactory.newLatLngZoom(l,18.0f);
        mapViewD = (SupportMapFragment)(getSupportFragmentManager().findFragmentById(R.id.dad_frag));
        mapViewD.getBaiduMap().setMapStatus(u2);
        mBaiduMapD = mapViewD.getBaiduMap();
        mBaiduMapD.setMapStatus(u2);
        BitmapDescriptor bitmapDescriptorD = BitmapDescriptorFactory.fromResource(R.drawable.ic_marke);
        OverlayOptions overlayOptionsD = new MarkerOptions().position(l).icon(bitmapDescriptorD);
        mBaiduMapD.addOverlay(overlayOptionsD);


        initView();
        initToolbar();
        //parent_bnv.performClick();
        parent_bnv.getMenu().getItem(2).setChecked(true);
        parent_bnv.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    //  界面跳转方法 本界面刷新 新界面就新建一个
                    @Override
                    public boolean onNavigationItemSelected(@Nullable MenuItem item) {
                        int num = item.getOrder();
                        if(num ==1)
                            startActivity(new Intent(ParentActivity.this,StartActivity.class));
                        else if(num == 2)
                            startActivity(new Intent(ParentActivity.this,SquareActivity.class));
                        else if(num == 4)
                            startActivity(new Intent(ParentActivity.this,MyInfoActivity.class));
                        else
                            return false;

                        finish();
                        // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
        );
        //parent_bnv.setSelectedItemId(R.id.bnv_parent);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.parent_tb);
        setSupportActionBar(mToolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parent_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.parent_calendar_button:
                Intent i = new Intent();
                i.setClass(ParentActivity.this,CalendarActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //初始化imageview
    private void initView() {
        iv_dad = findViewById(R.id.parent_imageView_dad);
        iv_mom = findViewById(R.id.parent_imageView_mom);
        iv_dad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 1;
                showChoosePicDialog();
            }
        });
        iv_mom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                showChoosePicDialog();
            }
        });
    }

    //显示头像
    private void showChoosePicDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items={"本地照片","拍摄一张"};
        builder.setNegativeButton("取消",null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE:
                        Intent localAlubm = new Intent(
                                Intent.ACTION_GET_CONTENT
                        );
                        localAlubm.setType("image/");
                        startActivityForResult(localAlubm, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent mineCamera = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        mineCamera.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(mineCamera, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                            setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "图片路径不存在.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
      protected void setImageToView(Intent data){
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = ImageUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
            switch(count){
                case 0:
                    iv_mom.setImageBitmap(photo);
                    break;
                case 1:
                    iv_dad.setImageBitmap(photo);
                    break;
            }
        }
    }


}