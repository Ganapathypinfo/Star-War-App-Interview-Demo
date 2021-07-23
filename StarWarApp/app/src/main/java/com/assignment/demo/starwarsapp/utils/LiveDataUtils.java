package com.assignment.demo.starwarsapp.utils;

import androidx.lifecycle.MutableLiveData;

import com.assignment.demo.starwarsapp.base.BaseApiResponseModel;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class LiveDataUtils {


    public static <T> void updateStatus(final MutableLiveData<BaseApiResponseModel<T>> liveData, final Single<T> future) {
        updateStatus(liveData, future.toObservable());
    }

    public static <T> void updateStatus(final MutableLiveData<BaseApiResponseModel<T>> liveData, final Observable<T> future) {
        // TODO Check this for Loading
        // liveData.postValue(new BaseApiResponseModel<>());
        future.subscribe(result -> {
            liveData.postValue(new BaseApiResponseModel<>(true, result, null));
        }, err -> {
            // TODO: here we need to find a better pattern for including the logger
            liveData.postValue(new BaseApiResponseModel<>(false, null, err.getLocalizedMessage()));
        });
    }
}
