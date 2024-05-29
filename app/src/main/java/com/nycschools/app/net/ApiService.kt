package com.nycschools.app.net

import com.nycschools.app.model.ApiResponse
import com.nycschools.app.model.ApiSATResponse
import io.reactivex.rxjava3.core.Maybe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("s3k6-pzi2.json")
    fun getSchInNyc(): Call<List<ApiResponse>>

    @GET("s3k6-pzi2.json")
    fun getSchInNycRxJava(): Maybe<List<ApiResponse>>

    @GET("s3k6-pzi2.json")
    suspend fun getCoroutineCall(): List<ApiResponse>


    @GET("f9bf-2cp4.json")
    fun getSAT(@Query("dbn") dbn : String): Call<List<ApiSATResponse>>
}