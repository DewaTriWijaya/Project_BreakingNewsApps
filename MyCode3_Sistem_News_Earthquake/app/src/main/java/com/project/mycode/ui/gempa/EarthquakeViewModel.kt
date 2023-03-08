package com.project.mycode.ui.gempa

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.mycode.data.RepositorySource
import com.project.mycode.data.local.NewEarthquakeDB
import com.project.mycode.data.local.FeltEarthquakeDB
import com.project.mycode.network.ResultResponse

class EarthquakeViewModel(private val repositorySource: RepositorySource): ViewModel() {

    fun earthquakeNew(): LiveData<ResultResponse<NewEarthquakeDB>> =
        repositorySource.getEarthquakeNew()

    fun feltEarthquake(): LiveData<ResultResponse<List<FeltEarthquakeDB>>> =
        repositorySource.getEarthquakeFelt()

}