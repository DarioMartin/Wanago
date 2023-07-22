package com.architectcoders.wanago.di

import android.app.Application
import androidx.room.Room
import com.architectcoders.wanago.BuildConfig
import com.architectcoders.wanago.data.database.EventDatabase
import com.architectcoders.wanago.data.server.RemoteService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    private const val TEST_NETWORK_PAGE_SIZE = 1
    private const val MOCK_SERVER_URL = "http://localhost:8080"

    @Provides
    @Reusable
    @ApiKey
    fun provideApiKey(app: Application): String = BuildConfig.ticketMasterApiKey

    @Provides
    @NetworkPageSize
    fun provideNetworkPageSize(): Int = TEST_NETWORK_PAGE_SIZE

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.inMemoryDatabaseBuilder(
        app,
        EventDatabase::class.java
    ).build()

    @Provides
    @Singleton
    fun provideEventDao(db: EventDatabase) = db.eventDao()

    @Provides
    @Reusable
    @ApiUrl
    fun provideApiUrl(): String = MOCK_SERVER_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    @Provides
    @Singleton
    fun provideRemoteService(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): RemoteService {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}
