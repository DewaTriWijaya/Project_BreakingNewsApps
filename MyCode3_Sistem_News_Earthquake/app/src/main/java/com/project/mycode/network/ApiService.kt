package com.project.mycode.network

import com.project.mycode.network.response.InfoEarthquakeNew
import com.project.mycode.network.response.InfoEarthquakeFelt
import retrofit2.http.GET

interface ApiService {

    @GET("autogempa.json")
    suspend fun getEarthquakeNew(): InfoEarthquakeNew

    @GET("gempaterkini.json")
    suspend fun getEarthquakeFelt(): InfoEarthquakeFelt


}