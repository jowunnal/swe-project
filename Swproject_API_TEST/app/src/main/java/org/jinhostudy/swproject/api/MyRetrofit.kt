package org.jinhostudy.swproject.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyRetrofit {
    private const val BASE_URL="http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/" //베이스주소

    private val okHttpClient: OkHttpClient by lazy { //인터셉터를 위한 okhttpclient 객체builder로 생성
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit: Retrofit by lazy { // retrofit 객체 빌더로 생성
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    val iRetrofit: IRetrofit by lazy { // retrofit 인터페이스 객체생성
        retrofit.create(IRetrofit::class.java)
    }
}