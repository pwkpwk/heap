package org.example.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    const val BASE_URI = "https://jsonplaceholder.typicode.com/"

    val blogApi: BlogApi by lazy {
        retrofit.create()
    }

    fun shutdown() {
        httpClient.dispatcher().executorService().shutdown()
        httpClient.connectionPool().evictAll()
    }

    private val httpClient: OkHttpClient by lazy { OkHttpClient.Builder().build() }

    private val retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URI)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}