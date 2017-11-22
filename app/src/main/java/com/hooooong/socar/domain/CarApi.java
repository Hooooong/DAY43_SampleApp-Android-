package com.hooooong.socar.domain;

import com.hooooong.socar.domain.model.Car;
import com.hooooong.socar.domain.model.CarData;
import com.hooooong.socar.domain.retrofit.iCar;
import com.hooooong.socar.util.ServiceGenerator;

import java.io.UnsupportedEncodingException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Hong on 2017-11-22.
 */

public class CarApi {
    public static List<CarData> data;

    public static void getCars(Callback callback) throws UnsupportedEncodingException {
        getCars(callback, "");
    }

    // 기존 코드를 사용할 수 있게 재정의를 한다.
    public static void getCars(Callback callback, String area) throws UnsupportedEncodingException {
        iCar service = ServiceGenerator.create(iCar.class);
        Observable<Car> observable = service.getData(area);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(car -> {
                    if(car.isSuccess()){
                        data = car.getCarData();
                        callback.initCar();
                    }
                });
    }

    public interface Callback {
        void initCar();
    }
}
