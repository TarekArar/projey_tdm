package com.laidi_arar.appQuran.ui.main


import com.laidi_arar.appQuran.model.ui.Racine
import com.laidi_arar.appQuran.repository.main.Repository
import com.laidi_arar.appQuran.ui.BaseViewModel
import com.laidi_arar.appQuran.ui.main.state.MainStateEvent.*
import com.laidi_arar.appQuran.ui.main.state.MainViewState
import com.laidi_arar.appQuran.util.*
import com.laidi_arar.appQuran.util.Constants.Companion.INVALID_STATE_EVENT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModel(
   private val repository: Repository
)  : BaseViewModel<MainViewState>() {

    override fun handleNewData(data: MainViewState) {
        data.let {
            it.racineModelList?.let {
                setRacine(it)
            }
        }
    }

    private fun setRacine(newData : List<Racine>){
        val update = getCurrentViewStateOrNew()
        if (update.racineModelList == newData){
            return
        }
        update.racineModelList = newData
        setViewState(update)
    }
    fun clearRacineSearch(){
        setRacine(emptyList())
    }
    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<MainViewState>> = when(stateEvent){
            is GetAyatFromNetwork -> {
                repository.getAllAyat(stateEvent = stateEvent)
            }
            is GetSurahFromNetwork -> {
                repository.getAllSurah(stateEvent = stateEvent)
            }
            is GetWorldFromNetwork -> {
                repository.getAllWorlds(stateEvent =stateEvent)
            }
            is GetRacineFromNetwork -> {
                repository.getAllRacine(stateEvent =stateEvent)
            }
            is SearchByRacine ->{
                repository.searchByRacine(
                    stateEvent = stateEvent ,
                    query = stateEvent.query
                )
            }

            else -> {
                flow{
                    emit(
                        DataState.error<MainViewState>(
                            response = Response(
                                message = INVALID_STATE_EVENT,
                                uiComponentType = UIComponentType.None(),
                                messageType = MessageType.Error()
                            ),
                            stateEvent = stateEvent
                        )
                    )
                }
            }
        }
        launchJob(stateEvent, job)
    }
    override fun initNewViewState(): MainViewState {
        return MainViewState()
    }
    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }

}