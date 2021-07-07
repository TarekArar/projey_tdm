package com.laidi_arar.appQuran.repository.main


import android.util.Log
import com.laidi_arar.appQuran.model.database.Ayat
import com.laidi_arar.appQuran.model.database.RacineModel
import com.laidi_arar.appQuran.model.database.Surah
import com.laidi_arar.appQuran.model.database.World
import com.laidi_arar.appQuran.model.ui.Racine
import com.laidi_arar.appQuran.network.WebService
import com.laidi_arar.appQuran.perssistance.QuranDao
import com.laidi_arar.appQuran.repository.safeApiCall
import com.laidi_arar.appQuran.repository.safeCacheCall
import com.laidi_arar.appQuran.ui.main.state.MainViewState
import com.laidi_arar.appQuran.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RepositoryImpl(
    private val webService: WebService,
    val quranDao: QuranDao,
) : Repository {

    private val TAG: String = "AppDebug"

    override fun getAllSurah(
        stateEvent: StateEvent,
    ): Flow<DataState<MainViewState>> = flow {

        val apiResult = safeApiCall(IO) {
            webService.getAllSurah()
        }
        emit(
            object : ApiResponseHandler<MainViewState, List<Surah>>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: List<Surah>): DataState<MainViewState> {

                    CoroutineScope(IO).launch {
                        for (surah in resultObj) {
                            launch {
                                try {
                                    quranDao.insertSurah(surah)
                                } catch (e: Exception) {
                                    Log.e(TAG, "error on inserting surah ${surah.surahName}")

                                }
                            }
                        }
                    }
                    return DataState.data(
                        data = MainViewState(),
                        stateEvent = stateEvent,
                        response = null
                    )
                }
            }.getResult()
        )
    }

    override fun getAllAyat(stateEvent: StateEvent): Flow<DataState<MainViewState>>  =flow {

        val apiResult = safeApiCall(IO) {
            webService.getAllAyat()
        }
        emit(
            object : ApiResponseHandler<MainViewState, List<Ayat>>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: List<Ayat>): DataState<MainViewState> {
                    CoroutineScope(IO).launch {
                        for (ayat in resultObj) {
                            launch {
                                try {
                                    quranDao.insertAyat(ayat)
                                } catch (e: Exception) {
                                    Log.e(TAG, "error on inserting ayat ${ayat.id}")
                                }
                            }
                        }
                    }
                    return DataState.data(
                        data = MainViewState(),
                        stateEvent = stateEvent,
                        response = null
                    )
                }
            }.getResult()
        )
    }

    override fun getAllWorlds(stateEvent: StateEvent) =flow {

        val apiResult = safeApiCall(IO) {
            webService.getAllWorlds()
        }
        emit(
            object : ApiResponseHandler<MainViewState, List<World>>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: List<World>): DataState<MainViewState> {
                    for (world in resultObj) {
                        try {
                            quranDao.insertWorld(world)
                        } catch (e: Exception) {
                            Log.e(TAG, "error on inserting word ${world.id}")
                        }
                    }
                    return DataState.data(
                        data = MainViewState(),
                        stateEvent = stateEvent,
                        response = null
                    )
                }
            }.getResult()
        )
    }


    override fun getAllRacine(stateEvent: StateEvent) =flow {

        val apiResult = safeApiCall(IO) {
            webService.getAllRacine()
        }
        emit(
            object : ApiResponseHandler<MainViewState, List<RacineModel>>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: List<RacineModel>): DataState<MainViewState> {
                   CoroutineScope(IO).launch {
                       for (racine in resultObj) {
                           launch {
                               try {
                                   quranDao.insertRacine(racine)
                               } catch (e: Exception) {
                                   Log.e(TAG, "error on inserting racine ${racine.id}")
                               }
                           }
                       }
                   }
                    return DataState.data(
                        data = MainViewState(),
                        stateEvent = stateEvent,
                        response = null
                    )
                }
            }.getResult()
        )
    }

    override fun searchByRacine(
        stateEvent: StateEvent,
        query: String
    ): Flow<DataState<MainViewState>> = flow{
         val cacheRequest =  safeCacheCall(IO){
             quranDao.searchByRacineUi(query)
         }
        emit(
            object : CacheResponseHandler<MainViewState , List<Racine>?>(
                stateEvent = stateEvent ,
                response = cacheRequest
            ){
                override suspend fun handleSuccess(resultObj: List<Racine>?): DataState<MainViewState> {
                    return DataState.data(
                        data = MainViewState(
                            racineModelList =  resultObj
                        ),
                        stateEvent = stateEvent,
                        response = null
                    )
                }
            }.getResult()
        )
    }
}
