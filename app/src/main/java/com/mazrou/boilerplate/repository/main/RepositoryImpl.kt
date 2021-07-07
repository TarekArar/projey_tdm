package com.mazrou.boilerplate.repository.main


import android.util.Log
import com.mazrou.boilerplate.model.database.AyatModel
import com.mazrou.boilerplate.model.database.RacineModel
import com.mazrou.boilerplate.model.database.Surah
import com.mazrou.boilerplate.model.database.World
import com.mazrou.boilerplate.model.ui.Ayat
import com.mazrou.boilerplate.model.ui.Racine
import com.mazrou.boilerplate.network.TafseerWebService
import com.mazrou.boilerplate.network.WebService
import com.mazrou.boilerplate.perssistance.QuranDao
import com.mazrou.boilerplate.repository.safeApiCall
import com.mazrou.boilerplate.repository.safeCacheCall
import com.mazrou.boilerplate.ui.main.state.MainViewState
import com.mazrou.boilerplate.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RepositoryImpl(
    private val webService: WebService,
    val quranDao: QuranDao,
    val tafseerWebService: TafseerWebService
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
            object : ApiResponseHandler<MainViewState, List<AyatModel>>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: List<AyatModel>): DataState<MainViewState> {
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
                            racineList =  resultObj
                        ),
                        stateEvent = stateEvent,
                        response = null
                    )
                }
            }.getResult()
        )
    }

    override fun searchAyatByRacine(
        stateEvent: StateEvent,
        racineId: String
    ): Flow<DataState<MainViewState>> = flow{
        val cacheRequest =  safeCacheCall(IO){
            quranDao.searchAyatByRacine(racineId)
        }
        emit(
            object : CacheResponseHandler<MainViewState , List<Ayat>?>(
                stateEvent = stateEvent ,
                response = cacheRequest
            ){
                override suspend fun handleSuccess(resultObj: List<Ayat>?): DataState<MainViewState> {
                    Log.e(TAG , "list of the ayat is : ${resultObj}")
                    return DataState.data(
                        data = MainViewState(
                            ayatRacineList =  resultObj
                        ),
                        stateEvent = stateEvent,
                        response = null
                    )
                }
            }.getResult()
        )

    }
}
