package com.assignment.demo.starwarsapp.retrofit

import android.content.Context
import com.assignment.demo.starwarsapp.BuildConfig
import com.assignment.demo.starwarsapp.base.BaseRetrofit
import retrofit2.Retrofit
import javax.inject.Inject

class ApiRetrofit @Inject constructor(private val context: Context) : BaseRetrofit() {

    private lateinit var retrofit: Retrofit

    fun getRetrofitInstance(): Retrofit {
        retrofit =
            getRetrofitInstance(context, BuildConfig.BASE_URL)
        return retrofit
    }
}