package com.solar.presentation.station_detail

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.solar.common.Resource
import com.solar.domain.repository.SolarRepo
import com.solar.domain.use_case.get_station_detail.GetStationDetailUseCase
import com.solar.domain.use_case.get_station_list.GetStationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StationDetailViewModel @Inject constructor(
    private val getStationDetailUseCase: GetStationDetailUseCase,
    private val getStationListUseCase: GetStationListUseCase,
    @ApplicationContext private val ctx: Context,
    private val repo: SolarRepo,
) :
    ViewModel() {
    private val _state = mutableStateOf(StationDetailState())
    val state: State<StationDetailState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            fetchAndSetData()
        }
    }

    private suspend fun fetchAndSetData() {
        val storageFile = "credentials_in_storage"
        val storageKey = "stationId"
        try {
            // Get station Id from storage
            val sharedPref = ctx.getSharedPreferences(storageFile, Context.MODE_PRIVATE)
            var stationId = sharedPref
                .getString(storageKey, null)
            Log.d("STORAGE", "${stationId ?: "nothing"}")

            // If not in storage, call api and put station Id in storage
            if (stationId == null) {
                val stationId = repo.getStationList().body()?.data?.page?.records?.get(0)?.id

                sharedPref.edit().putString(storageKey, stationId)
                    .apply()
            }
            // Finally call station detail
            getStationDetail()
        } catch (e: Exception) {
            // Handle any exceptions
        }
    }

    fun setLoadingFalse() {
        _state.value = StationDetailState(isLoading = false)
    }

    fun getStationDetail() {
        getStationDetailUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = StationDetailState(solarState = result.data, isLoading = false)
                }

                is Resource.Error -> {
                    _state.value = StationDetailState(error = result.message ?: "unexpected error")
                }

                is Resource.Loading -> {
                    _state.value = StationDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
}