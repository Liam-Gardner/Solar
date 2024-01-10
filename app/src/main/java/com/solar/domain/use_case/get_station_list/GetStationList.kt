package com.solar.domain.use_case.get_station_list

import android.util.Log
import com.solar.common.Resource
import com.solar.data.remote.dto.StationListResponse
import com.solar.data.remote.dto.toSolarStatus
import com.solar.domain.model.SolarStatus
import com.solar.domain.model.StationListRecord
import com.solar.domain.repository.SolarRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetStationListUseCase @Inject constructor(private val repo: SolarRepo) {
    operator fun invoke(): Flow<Resource<String?>> = flow {
        try {
            emit(Resource.Loading())
            val stationId = repo.getStationList().body()?.data?.page?.records?.get(0)?.id
            emit(Resource.Success(stationId))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error("Possible internet connectivity issue"))
        }
    }
}