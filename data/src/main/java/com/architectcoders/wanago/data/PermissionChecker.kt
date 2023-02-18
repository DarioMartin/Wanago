package com.architectcoders.wanago.data

interface PermissionChecker {
    enum class Permission { COARSE_LOCATION }

    fun check(permission: Permission): Boolean
}
