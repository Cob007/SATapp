package com.nycschools

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.nycschools.app.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class BaseApplication : Application() {
    private val TAG = javaClass.simpleName
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    ) {
                        return true
                    }
                }
            } else {
                try {
                    val activeNetworkInfo = connectivityManager.activeNetworkInfo
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                        Log.i(TAG, "Network is available : true")
                        return true
                    }
                } catch (e: Exception) {
                    Log.i(TAG, "" + e.message)
                }
            }
        }
        Log.i(TAG, "Network is available : FALSE ")
        return false
    }

    companion object {
        @get:Synchronized
        var instance: BaseApplication? = null
            private set
        private var retrofit: Retrofit? = null
        @JvmStatic
        val retrofitClient: Retrofit?
            // will setup Retrofit class here
            get() {
                if (retrofit == null) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    val client = OkHttpClient.Builder()
                        .addInterceptor(httpLoggingInterceptor)
                        .build()
                    retrofit = Retrofit.Builder()
                        .client(client)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Constants.BASE_URL)
                        .build()
                }
                return retrofit
            }
    }
}
