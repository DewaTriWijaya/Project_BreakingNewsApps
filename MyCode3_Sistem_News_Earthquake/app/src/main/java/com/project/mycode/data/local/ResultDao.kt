package com.project.mycode.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ResultDao {

    // earthquake new
    @Query("SELECT * FROM new_earthquake_information ORDER BY Tanggal DESC")
    fun getEarthquakeNew(): LiveData<NewEarthquakeDB>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEarthquakeNew(earthquake: NewEarthquakeDB)


    // earthquake 5.0+
    @Query("SELECT * FROM felt_earthquake_information ORDER BY Tanggal DESC")
    fun getEarthquakeFelt(): LiveData<List<FeltEarthquakeDB>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEarthquakeFelt(earthquake: List<FeltEarthquakeDB>)

}
