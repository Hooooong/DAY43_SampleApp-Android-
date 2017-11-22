package com.hooooong.socar.domain.retrofit;

import com.hooooong.socar.domain.model.Car;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Android Hong on 2017-11-22.
 */

public interface iCar {
    @GET("car/{param1}")
    Observable<Car> getData(@Path("param1") String area);
}
