package com.example.retrofit3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        CoroutineScope(Dispatchers.IO).launch {
            val country = countryAPI.getItem()
            val list: ArrayList<CountryModel> = ArrayList()
            for (i in country) {
                list.add(CountryModel(i.name, i.population, ImageModel(i.flags.svg, i.flags.png)))
            }
            runOnUiThread {
                adapter.updateData(list)
            }
        }
    }
}