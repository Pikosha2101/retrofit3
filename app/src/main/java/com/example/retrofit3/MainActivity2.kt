package com.example.retrofit3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class MainActivity2 : AppCompatActivity() {
    private lateinit var sortedCountries: ArrayList<CountryModel>
    private lateinit var randomCountries : ArrayList<CountryModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val rbGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val chart = findViewById<BarChart>(R.id.chart)
        val button2 = findViewById<Button>(R.id.button2)

        sortedCountries = intent.getSerializableExtra("list1") as ArrayList<CountryModel>
        randomCountries = intent.getSerializableExtra("list2") as ArrayList<CountryModel>
        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()
        rbGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb1 -> {
                    entries.clear()
                    labels.clear()
                    for ((index, item) in sortedCountries.withIndex()) {
                        entries.add(BarEntry(item.population.toFloat(), index))
                        labels.add(item.name)
                    }
                }

                R.id.rb2 -> {
                    entries.clear()
                    labels.clear()
                    for ((index, item) in randomCountries.withIndex()) {
                        entries.add(BarEntry(item.population.toFloat(), index))
                        labels.add(item.name)
                    }
                }
            }
            val dataset = BarDataSet(entries,"")

            dataset.setColors(ColorTemplate.COLORFUL_COLORS)
            val data = BarData(labels, dataset)
            chart.data = data
            chart.animateY(1000)
        }

        button2.setOnClickListener {
            val intent = Intent(this@MainActivity2, MainActivity::class.java)
            startActivity(intent)
        }
    }
}