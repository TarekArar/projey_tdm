package com.mazrou.boilerplate.model.ui

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Tafseer(
    @SerializedName("tafseer_id")
    @Expose
    val tafseerId : Int ,
    @SerializedName("tafseer_name")
    @Expose
    val tafseerName : String ,
    @SerializedName("ayah_url")
    @Expose
    val tafseerURL : String ,
    @SerializedName("ayah_number")
    @Expose
    val ayahNumber : Int ,
    @SerializedName("text")
    @Expose
    val text : String ,
)
