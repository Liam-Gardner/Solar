package com.solar.data.repository

import android.content.Context
import android.util.Log
import com.solar.data.remote.SolarApi
import com.solar.data.remote.StationDetailRequestBody
import com.solar.data.remote.StationListRequestBody
import com.solar.data.remote.dto.SolarResponseGen
import com.solar.data.remote.dto.StationListResponse
import com.solar.domain.repository.SolarRepo
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response

class SolarRepoImpl(
    private val api: SolarApi,
    private val ctx: Context
) :
    SolarRepo {
    override suspend fun getStationList(): Response<StationListResponse> {
        val content = "{\"pageNo\":1,\"pageSize\":10}"
        val headers = CreateHeaders(
            content = content,
            resource = "/v1/api/userStationList",
            verb = "POST",
        )

        return api.getStationList(
            headers.authorization,
            headers.date,
            headers.contentMD5,
            body = StationListRequestBody(pageNo = 1, pageSize = 10)
        )
    }

    override suspend fun getStationDetail(): Response<SolarResponseGen> {

        val stationId = getStationId(ctx)
        val content = "{\"id\": ${stationId}}"
        val headers = CreateHeaders(
            content = content,
            resource = "/v1/api/stationDetail",
            verb = "POST",
        )
        val stationIdToLong = stationId?.toLong() ?: 0

        return api.getStationDetail(
            headers.authorization,
            headers.date,
            headers.contentMD5,
            body = StationDetailRequestBody(id = stationIdToLong)
        )
    }
}

fun getStationId(ctx: Context): String? {
    val storageFile = "credentials_in_storage"
    val storageKey = "stationId"
    val sharedPref = ctx.getSharedPreferences(storageFile, Context.MODE_PRIVATE)
    var stationId = sharedPref
        .getString(storageKey, null)
    Log.d("STORAGE", "${stationId ?: "nothing"}")

    return stationId
}
