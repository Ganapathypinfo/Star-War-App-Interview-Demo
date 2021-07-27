package com.assignment.demo.starwarsapp.base

import android.content.Context
import com.assignment.demo.starwarsapp.constants.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class BaseRetrofit {

    private lateinit var retrofit: Retrofit

    protected fun getRetrofitInstance(context: Context, url: String): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(getSecuredOkHttpClient(context))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    private fun getSecuredOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getLogger())
            .connectTimeout(AppConstants.TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(AppConstants.TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(AppConstants.TIME_OUT.toLong(), TimeUnit.SECONDS)
            .build()
    }

    private fun getLogger(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}