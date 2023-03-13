package com.example.myapplication.network

import com.example.myapplication.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
//retrofit builder class for performing network calling.
object RetrofitBuilder {

        private var apiClient: RetrofitBuilder? = null
        private var retrofit: Retrofit? = null



        init {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            //create okhhtpclient to intercept the request and responses in logcat.

            val client: OkHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()

            //create retrofit builder object by passing base url,converter factory and client.


            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        @Synchronized
        fun getRetrofitBuilder() : RetrofitBuilder {
            if (apiClient == null) {
                apiClient =
                    RetrofitBuilder
            }
            return apiClient as RetrofitBuilder
        }

        fun getApi(): APIs {
            return retrofit!!.create(APIs::class.java)
        }

}