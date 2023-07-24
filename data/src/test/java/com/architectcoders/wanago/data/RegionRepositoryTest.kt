package com.architectcoders.wanago.data

import com.architectcoders.wanago.data.PermissionChecker.Permission.COARSE_LOCATION
import com.architectcoders.wanago.data.datasource.LocationDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

  @Test
  fun `returns default region when coarse permission not granted`(): Unit = runBlocking {
    val regionRepository = buildRegionRepository(
      permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn false }
    )

    val region = regionRepository.findLastRegion()

    assertEquals(RegionRepository.DEFAULT_REGION, region)
  }

  @Test
  fun `returns region from location data source when permission granted`(): Unit = runBlocking {
    val regionRepository = buildRegionRepository(
      locationDataSource = mock { onBlocking { findLastRegion() } doReturn "ES" },
      permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn true }
    )

    val region = regionRepository.findLastRegion()

    assertEquals("ES", region)
  }
}

private fun buildRegionRepository(
  locationDataSource: LocationDataSource = mock(),
  permissionChecker: PermissionChecker = mock()
) = RegionRepository(locationDataSource, permissionChecker)
