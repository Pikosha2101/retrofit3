package com.example.retrofit3

import retrofit2.http.GET

interface JsonAPI {
    @GET("all")
    suspend fun getItem() : ArrayList<CountryModel>
}