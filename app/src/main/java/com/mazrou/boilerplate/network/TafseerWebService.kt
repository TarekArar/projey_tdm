package com.mazrou.boilerplate.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mazrou.boilerplate.model.database.AyatModel
import com.mazrou.boilerplate.model.database.RacineModel
import com.mazrou.boilerplate.model.database.Surah
import com.mazrou.boilerplate.model.database.World
import com.mazrou.boilerplate.model.ui.Tafseer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL_TAFSEER = "http://api.quran-tafseer.com/"
interface TafseerWebService  {

    @GET("/tafseer/{tafseer_id}/{surah_number}/{ayah_number}")
    suspend fun getAyatTafseer(
        @Path("tafseer_id") tafseerId  : Int = 1 ,
        @Path("surah_number") surahNumber  : Int  ,
        @Path("ayah_number") ayatNumber  : Int  ,
    ): Tafseer

    companion object {

        fun invoke(): TafseerWebService {

            val gson: Gson = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val loggingHeader = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.HEADERS

            val httpClient = OkHttpClient.Builder()
                .callTimeout(5, java.util.concurrent.TimeUnit.MINUTES)
                .connectTimeout(1000, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(1000, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(1000, java.util.concurrent.TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(loggingHeader)


            return Retrofit.Builder()
                .baseUrl(BASE_URL_TAFSEER)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(TafseerWebService::class.java)
        }
    }
}