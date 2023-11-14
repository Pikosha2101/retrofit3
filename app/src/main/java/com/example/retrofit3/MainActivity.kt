package com.example.retrofit3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: Adapter
    private lateinit var sortedCountries : ArrayList<CountryModel>
    private lateinit var randomCountries : ArrayList<CountryModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val button = findViewById<Button>(R.id.button)
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

            sortedCountries = country.sortedByDescending { it.population }
                .take(5) as ArrayList<CountryModel>

            randomCountries = country.shuffled().take(5) as ArrayList<CountryModel>

            runOnUiThread {
                adapter.updateData(country)
            }
        }

        button.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            intent.putExtra("list1", sortedCountries)
            intent.putExtra("list2", randomCountries)
            startActivity(intent)
        }
    }
}