package com.architectcoders.wanago.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DBEvent(
    @PrimaryKey() val id: String,
    val name: String,
    val imageUrl: String,
    val venue: String,
    val date: String,
    val summary: String,
    val isFavorite: Boolean)
