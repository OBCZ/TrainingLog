package com.baarton.traininglog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baarton.traininglog.db.IDatabaseModule
import com.baarton.traininglog.model.Duration
import com.baarton.traininglog.model.SportRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import kotlin.time.Duration.Companion.seconds


class AddRecordViewModel : ViewModel() {

    private val dbModule: IDatabaseModule by KoinJavaComponent.inject(IDatabaseModule::class.java)

    private val _addRecordState: MutableStateFlow<AddRecordViewState> = MutableStateFlow(AddRecordViewState())
    val addRecordState: StateFlow<AddRecordViewState> = _addRecordState

    fun onSportValueChanged(newValue: String) {
        _addRecordState.update { state ->
            state.copy(sportName = StateProperty(false, newValue))
        }
    }

    fun onSportLocationChanged(newValue: String) {
        _addRecordState.update { state ->
            state.copy(sportLocation = StateProperty(false, newValue))
        }
    }

    fun onSportDurationChanged(newValue: kotlin.time.Duration) {
        _addRecordState.update { state ->
            state.copy(sportDuration = StateProperty(false, newValue))
        }
    }

    //TODO call Repo not DB directly
    fun onSportRecordSaveClick() {
        _addRecordState.update { state ->
            state.copy(
                sportName = StateProperty(false, state.sportName.value),
                sportLocation = StateProperty(false, state.sportLocation.value),
                sportDuration = StateProperty(false, state.sportDuration.value)
            )
        }

        if (isInputValid()) {
            val recordToStore = with(_addRecordState.value) {
                SportRecord(
                    sportName = sportName.value,
                    sportLocation = sportLocation.value,
                    sportDuration = Duration(sportDuration.value)
                )
            }
            viewModelScope.launch {
                dbModule.db.sportRecordDao().insert(recordToStore)
                //TODO confirm toast?
            }
        } else {
            //TODO warning toast?
        }

    }

    private fun isInputValid(): Boolean {
        return with(_addRecordState.value) {
            sportName.isValid() && sportLocation.isValid() && sportDuration.isValid()
        }
    }

    data class AddRecordViewState(
        val sportName: StateProperty<String> = StateProperty(value = ""),
        val sportLocation: StateProperty<String> = StateProperty(value = ""),
        val sportDuration: StateProperty<kotlin.time.Duration> = StateProperty(value = 0.seconds)
    )

    class StateProperty<T>(
        private val init: Boolean = true,
        val value: T
    ) {
        fun isValid(): Boolean {
            return when (value) {
                is String -> value.isNotBlank() || init
                is kotlin.time.Duration -> value != 0.seconds || init
                else -> false //TODO review this
            }
        }
    }

}