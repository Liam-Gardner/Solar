package com.solar.domain.use_case.get_station_detail

import android.util.Log
import com.solar.common.Resource
import com.solar.data.remote.dto.toSolarStatus
import com.solar.domain.model.SolarStatus
import com.solar.domain.repository.SolarRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetStationDetailUseCase @Inject constructor(
    private val repo: SolarRepo,
) {
    operator fun invoke(): Flow<Resource<SolarStatus>> = flow {
        try {
            emit(Resource.Loading())
            val solarStatus = repo.getStationDetail().body()?.data?.toSolarStatus()!!
            emit(Resource.Success(solarStatus))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error("Possible internet connectivity issue"))
        }
    }
}