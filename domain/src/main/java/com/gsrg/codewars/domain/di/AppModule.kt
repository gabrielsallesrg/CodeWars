package com.gsrg.codewars.domain.di

import android.content.Context
import com.gsrg.codewars.database.CodeWarsDatabase
import com.gsrg.codewars.domain.BuildConfig
import com.gsrg.codewars.domain.MockInterceptor
import com.gsrg.codewars.domain.api.CodeWarsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideClient(@ApplicationContext applicationContext: Context): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder().apply {
            addInterceptor(
                if (BuildConfig.MOCK_RESPONSE) {
                    MockInterceptor(applicationContext)
                } else {
                    logger
                }
            )
        }.build()
    }

    @Singleton
    @Provides
    fun provideCodeWarsApiService(client: OkHttpClient): CodeWarsApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.CODE_WARS_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CodeWarsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCodeWarsDatabase(@ApplicationContext applicationContext: Context): CodeWarsDatabase {
        return CodeWarsDatabase.getInstance(applicationContext)
    }
}