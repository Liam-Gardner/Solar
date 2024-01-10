package com.solar.data.remote

import com.solar.data.remote.dto.SolarResponseGen
import com.solar.data.remote.dto.StationListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

data class StationDetailRequestBody(val id: Long)
data class StationListRequestBody(val pageNo: Int, val pageSize: Int)

interface SolarApi {
    @POST("/v1/api/userStationList")
    suspend fun getStationList(
        @Header("Authorization") authorization: String,
        @Header("Time") time: String,
        @Header("Content-MD5") contentMD5: String,
        @Body body: StationListRequestBody
    ): Response<StationListResponse>

    @POST("/v1/api/stationDetail")
    suspend fun getStationDetail(
        @Header("Authorization") authorization: String,
        @Header("Time") time: String,
        @Header("Content-MD5") contentMD5: String,
        @Body body: StationDetailRequestBody
    ): Response<SolarResponseGen>
}
