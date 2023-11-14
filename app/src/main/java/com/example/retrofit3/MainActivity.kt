package com.example.retrofit3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = Adapter(ArrayList())

        recyclerView.adapter = adapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val countryAPI = retrofit.create(JsonAPI::class.java)


        val country : Call<ArrayList<CountryModel>> = countryAPI.getItem()
        country.enqueue(object : Callback<ArrayList<CountryModel>>{
            override fun onResponse(
                call: Call<ArrayList<CountryModel>>,
                response: Response<ArrayList<CountryModel>>
            ) {
                if (response.isSuccessful){
                    val list: ArrayList<CountryModel>? = response.body()
                    if (list != null) {
                        adapter.updateData(list)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<CountryModel>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Ошибка!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}