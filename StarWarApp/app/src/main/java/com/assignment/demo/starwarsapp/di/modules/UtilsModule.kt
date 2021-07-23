package com.assignment.demo.starwarsapp.di.modules

import com.assignment.demo.starwarsapp.retrofit.ApiRetrofit
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class UtilsModule {

    @Provides
    fun provideRetrofit(ApiRetrofit: ApiRetrofit): Retrofit {
        return ApiRetrofit.getRetrofitInstance()
    }
}