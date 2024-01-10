package com.solar.di

import android.content.Context
import android.content.SharedPreferences
import com.solar.common.Constants
import com.solar.data.remote.SolarApi
import com.solar.data.repository.SolarRepoImpl
import com.solar.domain.repository.SolarRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SolarAppModule {

    @Provides
    @Singleton
    fun provideSolarApi(): SolarApi {
        val client: OkHttpClient =
            OkHttpClient.Builder()
                .build()

        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        )
            .client(client)
            .build()
            .create(SolarApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSolarRepository(api: SolarApi, @ApplicationContext ctx: Context): SolarRepo {
        return SolarRepoImpl(api, ctx)
    }
}

