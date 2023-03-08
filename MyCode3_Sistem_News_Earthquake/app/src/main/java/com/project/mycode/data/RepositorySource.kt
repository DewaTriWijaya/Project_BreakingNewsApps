package com.project.mycode.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.project.mycode.data.local.NewEarthquakeDB
import com.project.mycode.data.local.FeltEarthquakeDB
import com.project.mycode.network.ApiService
import com.project.mycode.network.ResultResponse

class RepositorySource constructor(
    private val apiService: ApiService, private val resultDatabase: ResultDatabase
) {

    fun getEarthquakeNew(): LiveData<ResultResponse<NewEarthquakeDB>> = liveData {
        emit(ResultResponse.Loading)
        try {
            val response = apiService.getEarthquakeNew()
            val earthquake = response.infoEarthquakeNew
            val list = NewEarthquakeDB(
                earthquake.earthquakeNew.date,
                earthquake.earthquakeNew.clock,
                earthquake.earthquakeNew.magnitude,
                earthquake.earthquakeNew.depth,
                earthquake.earthquakeNew.region,
                earthquake.earthquakeNew.felt
            )
            resultDatabase.resultDao().insertEarthquakeNew(list)
        } catch (e: Exception) {
            Log.d("EarthquakeNew", "getEarthquakeNew: ${e.message.toString()}")
            emit(ResultResponse.Error(e.message.toString()))
        }

        val localData: LiveData<ResultResponse<NewEarthquakeDB>> =
            resultDatabase.resultDao().getEarthquakeNew().map { ResultResponse.Success(it) }
        emitSource(localData)
    }

    fun getEarthquakeFelt(): LiveData<ResultResponse<List<FeltEarthquakeDB>>> = liveData {
        emit(ResultResponse.Loading)
        try {
            val response = apiService.getEarthquakeFelt()
            val earthquake = response.infoEarthquakeFelt
            val list = earthquake.earthquakeFelt.map {
                FeltEarthquakeDB(
                    it.date, it.clock, it.magnitude, it.depth, it.region, it.potency
                )
            }
            resultDatabase.resultDao().insertEarthquakeFelt(list)
        } catch (e: Exception) {
            Log.d("EarthquakeFelt", "getEarthquakeFelt: ${e.message.toString()}")
            emit(ResultResponse.Error(e.message.toString()))
        }

        val localData: LiveData<ResultResponse<List<FeltEarthquakeDB>>> =
            resultDatabase.resultDao().getEarthquakeFelt().map { ResultResponse.Success(it) }
        emitSource(localData)
    }

    companion object {
        @Volatile
        private var instance: RepositorySource? = null
        fun getInstance(
            apiService: ApiService, resultDatabase: ResultDatabase
        ): RepositorySource = instance ?: synchronized(this) {
            instance ?: RepositorySource(apiService, resultDatabase)
        }.also { instance = it }
    }
}


