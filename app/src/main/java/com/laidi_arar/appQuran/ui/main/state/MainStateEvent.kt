package com.laidi_arar.appQuran.ui.main.state

import com.laidi_arar.appQuran.util.StateEvent

sealed class MainStateEvent : StateEvent {

    class GetAyatFromNetwork: MainStateEvent()

    class GetSurahFromNetwork: MainStateEvent()

    class GetWorldFromNetwork(): MainStateEvent()

    class GetRacineFromNetwork(): MainStateEvent()

    data class SearchByRacine(
        val query : String = ""
    ): MainStateEvent()

    class None: MainStateEvent()
}