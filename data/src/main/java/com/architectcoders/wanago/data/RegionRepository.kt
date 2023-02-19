package com.architectcoders.wanago.data

import com.architectcoders.wanago.data.PermissionChecker.Permission.COARSE_LOCATION
import com.architectcoders.wanago.data.datasource.LocationDataSource
import javax.inject.Inject

class RegionRepository @Inject constructor(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        private const val DEFAULT_REGION = "Madrid"
    }

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}
