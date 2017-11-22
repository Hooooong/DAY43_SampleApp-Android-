package com.hooooong.socar.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.hooooong.socar.R;
import com.hooooong.socar.domain.CarApi;
import com.hooooong.socar.domain.model.Data;
import com.hooooong.socar.domain.ZoneApi;
import com.hooooong.socar.view.adapter.CustomAdapter;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ZoneApi.Callback, CarApi.Callback {

    private String currentArea = "";
    private GoogleMap mMap;
    private ProgressBar mapProgressBar, recyclerViewProgressbar;
    private ClusterManager<MakerItem> clusterManager;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;

    private Map<String, LatLng> citys = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        citys.put("서울", new LatLng(37.515987, 127.020370));
        citys.put("부산", new LatLng(35.162201, 129.048703));
        citys.put("광주", new LatLng(35.152942, 126.843243));

        mapProgressBar = findViewById(R.id.mapProgressbar);
        recyclerViewProgressbar = findViewById(R.id.recyclerViewProgressbar);
        recyclerView = findViewById(R.id.recyclerView);
        customAdapter = new CustomAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);

        mapProgressBar.setVisibility(View.VISIBLE);
        try {

            ZoneApi.getZones(this);

        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Error 발생", Toast.LENGTH_SHORT).show();
            mapProgressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    @Override
    public void initZone() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void initCar() {
        recyclerViewProgressbar.setVisibility(View.GONE);
        customAdapter.setCarListAndRefresh(CarApi.data);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        clusterManager = new ClusterManager<>(this, mMap);

        // 클러스터 그룹 클릭시 처리
        clusterManager.setOnClusterClickListener(cluster -> {
            Toast.makeText(MapsActivity.this, "cluster 좌표 : " + cluster.getPosition().toString(), Toast.LENGTH_SHORT).show();
            return false;
        });

        // 단일 마커 클릭 시 처리 : 상세보기로 이동
        clusterManager.setOnClusterItemClickListener(cluster -> {
            setCarList(cluster.zone_id);
            return false;
        });

        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);

        setClusterOnMap();
    }

    private void setClusterOnMap() {
        mapProgressBar.setVisibility(View.GONE);
        clusterManager.clearItems();
        Observable<Data> observable = Observable.create(emitter -> {
            for (Data data : ZoneApi.data) {
                emitter.onNext(data);
            }
            emitter.onComplete();
        });

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            MakerItem offsetItem = new MakerItem(data.getLat(), data.getLng());
                            offsetItem.zone_id = data.getZone_id();
                            clusterManager.addItem(offsetItem);
                            // LatLng marker = new LatLng(data.getLat(), data.getLng());
                            // mMap.addMarker(new MarkerOptions().position(marker).title(data.getZone_name()));
                        },
                        throwable -> {
                        },
                        () -> {
                            LatLng coord;
                            // Add a marker in Sydney and move the camera
                            if (currentArea != null && !"".equals(currentArea)) {
                                coord = citys.get(currentArea);
                            } else {
                                coord = citys.get("서울");
                            }
                            // mMap.addMarker(new MarkerOptions().position(sinsa).title("Marker in Seoul"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, 11));
                        });
    }


    public void setAll(View view) {
        currentArea = "";
        mapProgressBar.setVisibility(View.VISIBLE);
        try {
            ZoneApi.getZones(this::setClusterOnMap);
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Error 발생", Toast.LENGTH_SHORT).show();
            mapProgressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    public void setSeoul(View view) {
        currentArea = "서울";
        mapProgressBar.setVisibility(View.VISIBLE);
        try {
            ZoneApi.getZones(this::setClusterOnMap, currentArea);
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Error 발생", Toast.LENGTH_SHORT).show();
            mapProgressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    public void setBusan(View view) {
        currentArea = "부산";
        mapProgressBar.setVisibility(View.VISIBLE);
        try {
            ZoneApi.getZones(this::setClusterOnMap, currentArea);
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Error 발생", Toast.LENGTH_SHORT).show();
            mapProgressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    public void setGwangju(View view) {
        currentArea = "광주";
        mapProgressBar.setVisibility(View.VISIBLE);
        try {
            ZoneApi.getZones(this::setClusterOnMap, currentArea);
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Error 발생", Toast.LENGTH_SHORT).show();
            mapProgressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    private void setCarList(String area) {
        recyclerViewProgressbar.setVisibility(View.VISIBLE);
        try {
            CarApi.getCars(this, area);
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Error 발생", Toast.LENGTH_SHORT).show();
            recyclerViewProgressbar.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    // ClusterItem 정의
    // LatLang 과 zone_id 를 정의한다.
    class MakerItem implements ClusterItem {
        private final LatLng mPosition;
        public String zone_id;

        public MakerItem(double lat, double lng) {
            mPosition = new LatLng(lat, lng);
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }
    }
}
