package com.example.retrofit3

import retrofit2.Call
import retrofit2.http.GET

interface JsonAPI {
    @GET("all")
    fun getItem() : Call<ArrayList<CountryModel>>
}