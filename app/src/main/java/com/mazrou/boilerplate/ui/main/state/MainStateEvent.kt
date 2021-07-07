package com.mazrou.boilerplate.ui.main.state

import com.mazrou.boilerplate.model.ui.Racine
import com.mazrou.boilerplate.util.StateEvent

sealed class MainStateEvent : StateEvent {

    class GetAyatFromNetwork: MainStateEvent()

    class GetSurahFromNetwork: MainStateEvent()

    class GetWorldFromNetwork(): MainStateEvent()

    class GetRacineFromNetwork(): MainStateEvent()

    data class SearchByRacine(
        val query : String = ""
    ): MainStateEvent()

    class GetAyatRacine(
        val racineId : String
    ): MainStateEvent()
    class None: MainStateEvent()
}