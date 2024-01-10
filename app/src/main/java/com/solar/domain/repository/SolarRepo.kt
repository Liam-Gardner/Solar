package com.solar.domain.repository

import com.solar.data.remote.dto.SolarResponseGen
import com.solar.data.remote.dto.StationListResponse
import retrofit2.Response

interface SolarRepo {
    suspend fun getStationList(): Response<StationListResponse>
    suspend fun getStationDetail(): Response<SolarResponseGen>
}