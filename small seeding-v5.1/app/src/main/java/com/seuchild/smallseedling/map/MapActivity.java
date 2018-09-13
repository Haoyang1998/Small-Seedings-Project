package com.seuchild.smallseedling.map;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.seuchild.smallseedling.R;
import com.seuchild.smallseedling.StartActivity;


public class MapActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    public static int flag = 0;
    //SDK定位核心类
    private LocationClient mLocationClient;
    //定位监听
    public MyLocationListenner myListener = new MyLocationListenner();
    //地图控件
    public MapView mMapView = null;
    //百度地图对象
    public BaiduMap mBaiduMap;
    //定位坐标
    private LatLng latLngF;
    private LatLng latLngM;
    private LatLng latLng;
    //判断首次定位
    boolean isFirstLoc = true;

    private static final int REQUEST_CODE_WRITE_SETTINGS =1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);

        requestWriteSettings();
        requestLocation();
        //初始化地图
        initMap();

        //初始化浮动菜单
        initFAB();

    }

    public void initMap() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        //获取百度地图对象
        mBaiduMap = mMapView.getMap();
        //地图类型
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        //logo
        mMapView.setLogoPosition(LogoPosition.logoPostionleftTop);
        //隐藏缩放按钮
        mMapView.showZoomControls(false);
        //比例尺
        mMapView. showScaleControl(true);
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        //定位核心类
        mLocationClient = new LocationClient(this);
        //注册监听
        mLocationClient.registerLocationListener(myListener);
        //定位配置信息
        initLocation();

        //开启定位
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // 打开gps

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // 构造定位数据
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // GPS定位结果
                    Toast.makeText(MapActivity.this, location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // 网络定位结果
                    Toast.makeText(MapActivity.this, location.getAddrStr(), Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 离线定位结果
                    Toast.makeText(MapActivity.this, location.getAddrStr(), Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(MapActivity.this, "服务器错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(MapActivity.this, "网络错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(MapActivity.this, "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
                }
            }

        }
        public void onReceivePoi(BDLocation poiLocation) {
        }

    }
    //申请权限
    private void requestLocation() {
        int checkPermission = ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }
    private void requestWriteSettings() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS );
    }

    //浮动按钮
    private void initFAB() {
        final ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_button));

        //创建FAB
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        //创建副fab
        SubActionButton.Builder subAB = new SubActionButton.Builder(this);
        ImageView subIcon1 = new ImageView(this);
        ImageView subIcon2 = new ImageView(this);
        ImageView subIcon3 = new ImageView(this);
        ImageView subIcon4 = new ImageView(this);
        ImageView subIcon5 = new ImageView(this);

        //副fab图标设计
        subIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_button_fab_map));
        subIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_button_fab_location));
        subIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_button_fab_father));
        subIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_button_fab_mom));
        subIcon5.setImageDrawable(getResources().getDrawable(R.drawable.ic_map_button_fab_return));

        //添加FAB菜单
        FloatingActionMenu faM = new FloatingActionMenu.Builder(this)
                .addSubActionView(subAB.setContentView(subIcon5).build())
                .addSubActionView(subAB.setContentView(subIcon1).build())
                .addSubActionView(subAB.setContentView(subIcon3).build())
                .addSubActionView(subAB.setContentView(subIcon4).build())
                .addSubActionView(subAB.setContentView(subIcon2).build())
                .attachTo(icon)
                .build();

        //开关菜单
        faM.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                //旋转
                icon.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator objAnimation = ObjectAnimator.ofPropertyValuesHolder(icon, pvhR);
                objAnimation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
                //旋转
                icon.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator objAnimation = ObjectAnimator.ofPropertyValuesHolder(icon, pvhR);
                objAnimation.start();
            }
        });
        //切换地图类型
        subIcon1.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == 0)
                {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    flag = 1;
                }else {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    flag =0;
                }

            }
        });
        //重定位
        subIcon2.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.requestLocation();
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                mBaiduMap.setMapStatus(msu);
                MapStatusUpdate msu1 = MapStatusUpdateFactory.zoomTo(16f);
                mBaiduMap.animateMapStatus(msu1);
            }
        });
        //父亲定位
        subIcon3.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                latLngF = new LatLng(35.448829,119.572439);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLngF);
                mBaiduMap.animateMapStatus(msu);
                mBaiduMap.setMapStatus(msu);
                MapStatusUpdate msu1 = MapStatusUpdateFactory.zoomTo(16f);
                mBaiduMap.animateMapStatus(msu1);
            }
        });
        //母亲定位
        subIcon4.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                latLngM = new LatLng(23.107903,102.744729);
                mLocationClient.requestLocation();
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLngM);
                mBaiduMap.animateMapStatus(msu);
                mBaiduMap.setMapStatus(msu);
                MapStatusUpdate msu1 = MapStatusUpdateFactory.zoomTo(16f);
                mBaiduMap.animateMapStatus(msu1);
            }
        });

        //返回上一页
        subIcon5.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

}
