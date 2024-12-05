package com.example.lab_8

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.*

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}

interface RetrofitServices {
    @GET
    fun getWeatherList(@Url url: String): Call<WeatherForecastResponse>
}

object Common {
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/forecast?lat=54.2021736&lon=30.2964015&appid=d70b749fd7a3d107010b8a56df59e94c"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}