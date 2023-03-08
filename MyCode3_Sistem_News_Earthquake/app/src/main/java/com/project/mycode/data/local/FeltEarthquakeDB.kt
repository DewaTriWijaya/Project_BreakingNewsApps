package com.project.mycode.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "felt_earthquake_information")
data class FeltEarthquakeDB(
    @ColumnInfo(name = "Tanggal")
    @PrimaryKey
    val date: String,

    @ColumnInfo(name = "Jam")
    val clock: String,

    @ColumnInfo(name = "Magnitude")
    val magnitude: String,

    @ColumnInfo(name = "Kedalaman")
    val depth: String,

    @ColumnInfo(name = "Wilayah")
    val region: String,

    @ColumnInfo(name = "Potensi")
    val potency: String
)