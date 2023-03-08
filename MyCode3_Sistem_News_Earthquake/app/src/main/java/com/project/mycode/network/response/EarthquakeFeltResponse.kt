package com.project.mycode.network.response

import com.google.gson.annotations.SerializedName

data class InfoEarthquakeFelt(
    @SerializedName("Infogempa")
    val infoEarthquakeFelt: EarthquakeFelt
)

data class EarthquakeFelt(
    @SerializedName("gempa")
    val earthquakeFelt: List<EarthquakeFeltList>
)

data class EarthquakeFeltList(
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

    @SerializedName("Potensi")
    val potency: String
)