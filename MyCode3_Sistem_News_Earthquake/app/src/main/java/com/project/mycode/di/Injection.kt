package com.project.mycode.di

import android.content.Context
import com.project.mycode.data.RepositorySource
import com.project.mycode.data.ResultDatabase
import com.project.mycode.network.ApiRetrofit

object Injection {
    fun provideRepository(context: Context): RepositorySource{
        val apiService = ApiRetrofit.getApiService()
        val database = ResultDatabase.getInstance(context)
        return RepositorySource.getInstance(apiService, database)
    }
}