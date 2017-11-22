package com.hooooong.socar.domain;

import com.hooooong.socar.domain.model.Data;
import com.hooooong.socar.domain.model.Zone;
import com.hooooong.socar.domain.retrofit.iZone;
import com.hooooong.socar.util.ServiceGenerator;

import java.io.UnsupportedEncodingException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by Android Hong on 2017-11-22.
 */

public class ZoneApi {

    public static List<Data> data;

    public static void getZones(Callback callback) throws UnsupportedEncodingException{
        getZones(callback, "");
    }

    // 기존 코드를 사용할 수 있게 재정의를 한다.
    public static void getZones(Callback callback, String area) throws UnsupportedEncodingException {

        iZone service = ServiceGenerator.create(iZone.class);
        Observable<Zone> observable = service.getData(area);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(zone -> {
                    if(zone.isSuccess()){

                        data = zone.getData();
                        callback.initZone();
                    }
                });
    }

    public interface Callback {
        void initZone();
    }
}
