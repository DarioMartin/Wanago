package com.architectcoders.wanago.domain

data class WanagoEvent(
    val id: String,
    val name: String,
    val imageUrl: String,
    val venue: String,
    val isFavorite: Boolean = false
)
