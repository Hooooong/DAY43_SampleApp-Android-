package com.hooooong.socar.domain.retrofit;

import com.hooooong.socar.domain.model.Zone;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Android Hong on 2017-11-22.
 */
// 인터페이스 생성
public interface iZone {
    @GET("zone/{param1}")
    Observable<Zone> getData(@Path("param1") String area);
}