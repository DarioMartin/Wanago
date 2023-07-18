package com.architectcoders.wanago.di

import android.app.Application
import androidx.room.Room
import com.architectcoders.wanago.BuildConfig
import com.architectcoders.wanago.data.AndroidPermissionChecker
import com.architectcoders.wanago.data.PermissionChecker
import com.architectcoders.wanago.data.PlayServicesLocationDataSource
import com.architectcoders.wanago.data.database.EventDatabase
import com.architectcoders.wanago.data.database.EventRoomDataSource
import com.architectcoders.wanago.data.datasource.EventsLocalDataSource
import com.architectcoders.wanago.data.datasource.EventsRemoteDataSource
import com.architectcoders.wanago.data.datasource.LocationDataSource
import com.architectcoders.wanago.data.server.TicketMasterDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val NETWORK_PAGE_SIZE = 25

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(): String = BuildConfig.ticketMasterApiKey

    @Provides
    @NetworkPageSize
    fun provideNetworkPageSize(): Int = NETWORK_PAGE_SIZE

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        EventDatabase::class.java, "event-db"
    ).build()

    @Provides
    @Singleton
    fun provideEventsDao(db: EventDatabase) = db.eventDao()

}

@Module
@InstallIn(SingletonComponent::class)
interface AppDataModule {

    @Binds
    fun bindLocalDataSource(localDataSource: EventRoomDataSource): EventsLocalDataSource

    @Binds
    fun bindRemoteDataSource(remoteDataSource: TicketMasterDataSource): EventsRemoteDataSource

    @Binds
    fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker

}

