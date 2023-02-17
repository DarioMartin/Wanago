package com.architectcoders.wanago.presentation.common

import android.content.Context
import com.architectcoders.wanago.App

val Context.app: App
    get() = applicationContext as App