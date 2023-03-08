package com.project.mycode.network.response

import com.google.gson.annotations.SerializedName


data class InfoEarthquakeNew(
    @SerializedName("Infogempa")
    val infoEarthquakeNew: EarthquakeNew
)

data class EarthquakeNew(
    @SerializedName("gempa")
    val earthquakeNew: EarthquakeNewList
)

data class EarthquakeNewList(
    @SerializedName("Tanggal")
    val date: String,

    @SerializedName("Jam")
    val clock: String,

    @SerializedName("Magnitude")
    val magnitude: String,

    @SerializedName("Kedalaman")
    val depth: String,

    @SerializedName("Wilayah")
    val region: String,

    @SerializedName("Dirasakan")
    val felt: String
)