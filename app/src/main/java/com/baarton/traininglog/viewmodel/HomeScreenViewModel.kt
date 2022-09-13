package com.baarton.traininglog.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baarton.traininglog.model.SportRecord
import com.baarton.traininglog.repo.IRepositoryModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent


class HomeScreenViewModel : ViewModel() {

    private val repositoryModule: IRepositoryModule by KoinJavaComponent.inject(IRepositoryModule::class.java)

    private val _homeScreenState: MutableStateFlow<HomeScreenViewState> = MutableStateFlow(HomeScreenViewState(loading = true))
    val homeScreenState: StateFlow<HomeScreenViewState> = _homeScreenState

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            repositoryModule.getRecords().collect {
                _homeScreenState.update { state ->
                    state.copy(loading = false, list = it)
                }
            }
        }
    }

    fun clearDatabase() {
        _homeScreenState.update {
            it.copy(loading = true)
        }
        viewModelScope.launch {
            repositoryModule.nuke()
        }
    }

    data class HomeScreenViewState(
        val loading: Boolean = false,
        val list: List<SportRecord> = listOf()
    )

}