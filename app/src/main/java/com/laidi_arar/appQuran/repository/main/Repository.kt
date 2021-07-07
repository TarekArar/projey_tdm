package com.laidi_arar.appQuran.repository.main

import com.laidi_arar.appQuran.ui.main.state.MainViewState
import com.laidi_arar.appQuran.util.DataState
import com.laidi_arar.appQuran.util.StateEvent
import kotlinx.coroutines.flow.Flow

interface Repository  {

    fun getAllSurah(
        stateEvent: StateEvent,
    ): Flow<DataState<MainViewState>>

    fun getAllAyat(
        stateEvent: StateEvent,
    ): Flow<DataState<MainViewState>>

    fun getAllWorlds(
        stateEvent: StateEvent
    ): Flow<DataState<MainViewState>>

    fun getAllRacine(
        stateEvent: StateEvent
    ): Flow<DataState<MainViewState>>

    fun searchByRacine(
        stateEvent: StateEvent ,
        query : String
    ): Flow<DataState<MainViewState>>

}