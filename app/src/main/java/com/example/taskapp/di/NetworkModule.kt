package com.example.taskapp.di

import android.content.Context
import androidx.transition.Visibility
import com.example.taskapp.data.remote.NetworkApi
import com.example.taskapp.utils.Constants.BASE_URL
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun getHttpInterceptor(@ApplicationContext context : Context) : OkHttpClient{
        return OkHttpClient.Builder()
//            .addInterceptor(ChuckInterceptor(context))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    @Provides
    fun getRetrofit(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun getNetworkApi(retrofit: Retrofit) : NetworkApi{
        return retrofit.create(NetworkApi::class.java)
    }
}