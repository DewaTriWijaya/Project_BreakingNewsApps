package com.project.mycode.welcome.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,
)

data class LoginResult(

    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("login")
    val users: LoginResponse,
)