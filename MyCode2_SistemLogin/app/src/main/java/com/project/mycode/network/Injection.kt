package com.project.mycode.network

import android.content.Context
import com.project.mycode.data.RepositorySource

object Injection {
    fun provideRepository(context: Context): RepositorySource{
        val apiService = ApiRetrofit.getApiService()
        return RepositorySource.getInstance(apiService)
    }
}