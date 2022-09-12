package com.baarton.traininglog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baarton.traininglog.db.IDatabaseModule
import com.baarton.traininglog.model.SportRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


class AddRecordViewModel : ViewModel() {

    private val dbModule: IDatabaseModule by KoinJavaComponent.inject(IDatabaseModule::class.java)

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

    fun onSportDurationChanged(newValue: Duration) {
        _addRecordState.update { state ->
            state.copy(sportDuration = newValue)
        }
    }

    //TODO input validations (empty fields)
    // call Repo not DB directly
    // tell user that the record has been added
    fun onSportRecordSaveClick() {
        val recordToStore = with(_addRecordState.value) {
            SportRecord(
                0,
                sportName,
                sportLocation,
                com.baarton.traininglog.model.Duration(sportDuration)
            )
        }
        viewModelScope.launch {
            dbModule.db.sportRecordDao().insert(recordToStore)
        }
    }

    data class AddRecordViewState(
        val sportName: String = "",
        val sportLocation: String = "",
        val sportDuration: Duration = 0.seconds
    )

}