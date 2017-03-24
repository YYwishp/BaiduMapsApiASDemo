/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package baidumapsdk.demo.map;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.clusterutil.MarkerManager;
import com.baidu.mapapi.clusterutil.clustering.Cluster;
import com.baidu.mapapi.clusterutil.clustering.ClusterItem;
import com.baidu.mapapi.clusterutil.clustering.ClusterManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.widget.Toast;
import baidumapsdk.demo.R;

/**
 * 此Demo用来说明点聚合功能
 */
public class MarkerClusterDemo extends Activity implements OnMapLoadedCallback {

    MapView mMapView;
    BaiduMap mBaiduMap;
    MapStatus ms;
    private ClusterManager<MyItem> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_cluster_demo);

        mMapView = (MapView) findViewById(R.id.bmapView);
        ms = new MapStatus.Builder().target(new LatLng(39.914935, 116.403119)).zoom(9).build();
        mBaiduMap = mMapView.getMap();
        //设置地图加载完成回调
        mBaiduMap.setOnMapLoadedCallback(this);
        //以动画方式更新地图状态
       // mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
        // 定义点聚合管理类ClusterManager
        mClusterManager = new ClusterManager<MyItem>(this, mBaiduMap);
        // 添加Marker点
        addMarkers();
      /*
        MarkerManager.Collection clusterMarkerCollection = mClusterManager.getClusterMarkerCollection();
        MarkerManager.Collection markerCollection = mClusterManager.getMarkerCollection();
        Collection<Marker> markers = clusterMarkerCollection.getMarkers();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker:markers) {
            LatLng position = marker.getPosition();
            builder.include(position);
        }
        LatLngBounds latLngBounds = builder.build();

        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(latLngBounds));*/






        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);

        // 设置maker点击时的响应
       mBaiduMap.setOnMarkerClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
                Toast.makeText(MarkerClusterDemo.this,
                        "有" + cluster.getSize() + "个点", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {
                Toast.makeText(MarkerClusterDemo.this,
                        "点击单个Item", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    /**
     * 向地图添加Marker点
     */
    public void addMarkers() {
        // 添加Marker点
        LatLng llA = new LatLng(34.963175, 116.400244);
        LatLng llB = new LatLng(34.942821, 116.369199);
        LatLng llC = new LatLng(34.939723, 116.425541);
        LatLng llD = new LatLng(34.906965, 116.401394);
        LatLng llE = new LatLng(34.956965, 116.331394);
        LatLng llF = new LatLng(34.886965, 120.441394);
        LatLng llG = new LatLng(34.996965, 116.411394);

        List<MyItem> items = new ArrayList<MyItem>();
        items.add(new MyItem(llA));
        items.add(new MyItem(llB));
        items.add(new MyItem(llC));
        items.add(new MyItem(llD));
        items.add(new MyItem(llE));
        items.add(new MyItem(llF));
        items.add(new MyItem(llG));

        mClusterManager.addItems(items);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MyItem myItem:items) {
            LatLng position = myItem.getPosition();
            builder.include(position);
        }
        LatLngBounds latLngBounds = builder.build();

//        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(latLngBounds));
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(latLngBounds.getCenter(),6));
        //mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(latLngBounds));

    }

    /**
     * 每个Marker点，包含Marker点坐标以及图标
     */
    public class MyItem implements ClusterItem {
        private final LatLng mPosition;

        public MyItem(LatLng latLng) {
            mPosition = latLng;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public BitmapDescriptor getBitmapDescriptor() {
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_gcoding);
        }
    }

    @Override
    public void onMapLoaded() {
        // TODO Auto-generated method stub
        ms = new MapStatus.Builder().zoom(9).build();
      //  mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
//        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
    }

}





















