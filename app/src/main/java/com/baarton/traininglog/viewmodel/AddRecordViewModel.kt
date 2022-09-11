package com.baarton.traininglog.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


class AddRecordViewModel : ViewModel() {


    private val _addRecordState: MutableStateFlow<AddRecordViewState> = MutableStateFlow(AddRecordViewState())
    val addRecordState: StateFlow<AddRecordViewState> = _addRecordState

    fun onSportValueChanged(newValue: String) {
        _addRecordState.update { state ->
            state.copy(sportName = newValue)
        }
    }

    fun onSportLocationChanged(newValue: String) {
        _addRecordState.update { state ->
            state.copy(sportLocation = newValue)
        }
    }

    fun onSportDurationChanged(newValue: String) {
        _addRecordState.update { state ->
            state.copy(sportDuration = Duration.parseIsoString(newValue)) //FIXME
        }
    }


    data class AddRecordViewState(
        val sportName: String = "",
        val sportLocation: String = "",
        val sportDuration: Duration = 0.seconds
    )

}