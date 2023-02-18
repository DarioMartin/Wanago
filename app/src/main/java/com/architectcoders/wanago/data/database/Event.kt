package com.architectcoders.wanago.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey() val id: String,
    val name: String,
    val imageUrl: String,
    val venue: String,
    val isFavorite: Boolean)
