package com.mazrou.boilerplate.ui.main.state


import com.mazrou.boilerplate.model.ui.Ayat
import com.mazrou.boilerplate.model.ui.Racine

const val AUTH_VIEW_STATE_BUNDLE_KEY = "com.arar.boilerplate.ui.auth.state.AuthViewState"


class MainViewState(
    var racineList: List<Racine>? = null ,
    var ayatRacineList:    List<Ayat> ? = null ,
    var selectedAyat : Ayat? = null

) //: Parcelable



