package com.project.mycode.welcome.login

import androidx.lifecycle.ViewModel
import com.project.mycode.data.RepositorySource

class LoginViewModel(private val repository: RepositorySource): ViewModel() {

    fun getLoginUser(loginResponse: LoginResponse) = repository.loginUser(loginResponse)

}