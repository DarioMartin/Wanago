package com.architectcoders.wanago

import android.app.Application
import androidx.room.Room
import com.architectcoders.wanago.data.database.EventDatabase

class App : Application() {
    lateinit var db: EventDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            EventDatabase::class.java, "event-db"
        ).build()
    }
}
