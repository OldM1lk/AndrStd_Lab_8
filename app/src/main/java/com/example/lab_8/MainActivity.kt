package com.example.lab_8

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Retrofit
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rView: RecyclerView = findViewById(R.id.rView)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        val adapter: Adapter = Adapter(DiffCallback())
        setSupportActionBar(toolbar)
        rView.adapter = adapter
        rView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/forecast?lat=54.2021736&lon=30.2964015&appid=d70b749fd7a3d107010b8a56df59e94c")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(RetrofitServices::class.java)
        val call = service.getWeatherList("https://api.openweathermap.org/data/2.5/forecast?lat=54.2021736&lon=30.2964015&appid=d70b749fd7a3d107010b8a56df59e94c")

        call.enqueue(object : Callback<WeatherForecastResponse> {
            override fun onResponse(call: Call<WeatherForecastResponse>, response: Response<WeatherForecastResponse>) {
                if (response.isSuccessful) {
                    val forecast = response.body()
                    adapter.submitList(forecast!!.list)
                }
            }

            override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) { }
        })
    }
}
