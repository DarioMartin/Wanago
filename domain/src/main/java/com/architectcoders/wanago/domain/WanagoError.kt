package com.architectcoders.wanago.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import retrofit2.HttpException
import java.io.IOException

sealed interface WanagoError {
    class Server(val code: Int) : WanagoError
    object Connectivity : WanagoError
    class Unknown(val message: String) : WanagoError
}

fun Throwable.toError(): WanagoError = when (this) {
    is IOException -> WanagoError.Connectivity
    is HttpException -> WanagoError.Server(code())
    else -> WanagoError.Unknown(message ?: "")
}

@Suppress("TooGenericExceptionCaught")
suspend fun <T> tryCall(action: suspend () -> T): Either<WanagoError, T> = try {
    action().right()
} catch (e: Exception) {
    e.toError().left()
}
