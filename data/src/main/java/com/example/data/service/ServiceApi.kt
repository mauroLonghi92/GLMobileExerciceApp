package com.example.data.service

import com.example.data.response.ItemResponse
import retrofit2.Call
import retrofit2.http.GET

interface ServiceApi {

    @GET("/list")
    fun getItems(): Call<List<ItemResponse>>

}