package com.architectcoders.wanago.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM DBEvent")
    fun getAll(): Flow<List<DBEvent>>

    @Query("SELECT * FROM DBEvent WHERE id = :id")
    fun getById(id: String): Flow<DBEvent>

    @Query("SELECT COUNT(id) FROM DBEvent")
    suspend fun eventCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(dbEvents: List<DBEvent>)
}
