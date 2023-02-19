package com.architectcoders.wanago.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DBEvent::class], version = 1, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
}
