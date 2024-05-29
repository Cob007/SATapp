package com.nycschools.app.repo

import com.nycschools.app.model.ApiResponse
import com.nycschools.app.model.ApiSATResponse
import com.practice.nextapplication.model.api.ApiClient
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.flow.flow as flow

class AppRepository {
    fun getSchools(result:(isSuccess : Boolean, response : List<ApiResponse>)-> Unit) {
        ApiClient.instance.getSchInNyc().enqueue(object : Callback<List<ApiResponse>> {
            override fun onResponse(
                call: Call<List<ApiResponse>>,
                response: Response<List<ApiResponse>>
            ) {
                if(response.isSuccessful){
                    result(true, response.body()!!)
                }else{
                    result(false, emptyList())
                }
            }

            override fun onFailure(call: Call<List<ApiResponse>>, t: Throwable) {
                result(false, emptyList())
            }

        })
    }


    suspend fun coroutineCallApi() : Flow<List<ApiResponse>>
        = flow {
            val response = ApiClient.instance.getCoroutineCall()
            emit(response)
        }



    fun getSatByDbn(dbn :String, result:(isSuccess : Boolean, response : List<ApiSATResponse>)-> Unit) {
        ApiClient.instance.getSAT(dbn).enqueue(object : Callback<List<ApiSATResponse>> {
            override fun onResponse(
                call: Call<List<ApiSATResponse>>,
                response: Response<List<ApiSATResponse>>
            ) {
                if(response.isSuccessful){
                    result(true, response.body()!!)

                }else{
                    result(false, emptyList())
                }
            }

            override fun onFailure(call: Call<List<ApiSATResponse>>, t: Throwable) {
                result(false, emptyList())
            }
        })
    }


    companion object {
        private var INSTANCE : AppRepository?= null
        fun getInstance() = INSTANCE ?: AppRepository().also {
            INSTANCE = it
        }
    }
}