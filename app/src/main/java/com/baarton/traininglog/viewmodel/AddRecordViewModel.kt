package com.baarton.traininglog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baarton.traininglog.model.Duration
import com.baarton.traininglog.model.SportRecord
import com.baarton.traininglog.repo.IRepositoryModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import kotlin.time.Duration.Companion.seconds


class AddRecordViewModel : ViewModel() {

    private val repositoryModule: IRepositoryModule by KoinJavaComponent.inject(IRepositoryModule::class.java)

    private val _addRecordState: MutableStateFlow<AddRecordViewState> = MutableStateFlow(AddRecordViewState())
    val addRecordState: StateFlow<AddRecordViewState> = _addRecordState

    fun onSportValueChanged(newValue: String) {
        _addRecordState.update { state ->
            state.copy(sportName = StateProperty(false, newValue))
        }
        updateFormValidity()
    }

    fun onSportLocationChanged(newValue: String) {
        _addRecordState.update { state ->
            state.copy(sportLocation = StateProperty(false, newValue))
        }
        updateFormValidity()
    }

    fun onSportDurationChanged(newValue: kotlin.time.Duration) {
        _addRecordState.update { state ->
            state.copy(sportDuration = StateProperty(false, newValue))
        }
        updateFormValidity()
    }

    private fun updateFormValidity() {
        _addRecordState.update { state -> state.copy(formIsValid = state.isValid()) }
    }

    fun onSportRecordSaveClick() {
        _addRecordState.update { state ->
            state.copy(
                sportName = StateProperty(false, state.sportName.value),
                sportLocation = StateProperty(false, state.sportLocation.value),
                sportDuration = StateProperty(false, state.sportDuration.value),
            )
        }
        updateFormValidity()

        with(_addRecordState.value) {
            if (isValid()) {
                val recordToStore = SportRecord(
                    sportName = sportName.value,
                    sportLocation = sportLocation.value,
                    sportDuration = Duration(sportDuration.value)
                )

                viewModelScope.launch {
                    repositoryModule.addRecord(recordToStore)
                    //TODO confirm toast?
                }
            }
        }

    }

    data class AddRecordViewState(
        val formIsValid: Boolean = true,
        val sportName: StateProperty<String> = StateProperty(value = ""),
        val sportLocation: StateProperty<String> = StateProperty(value = ""),
        val sportDuration: StateProperty<kotlin.time.Duration> = StateProperty(value = 0.seconds)
    ) {
        fun isValid(): Boolean {
            return sportName.isValid() && sportLocation.isValid() && sportDuration.isValid()
        }
    }

    class StateProperty<T>(
        private val init: Boolean = true,
        val value: T
    ) {
        fun isValid(): Boolean {
            return when (value) {
                is String -> value.isNotBlank() || init
                is kotlin.time.Duration -> value != 0.seconds || init
                else -> throw UnsupportedOperationException("Validity check for this type ${value?.let { it::class.java }} is not supported.")
            }
        }
    }

}