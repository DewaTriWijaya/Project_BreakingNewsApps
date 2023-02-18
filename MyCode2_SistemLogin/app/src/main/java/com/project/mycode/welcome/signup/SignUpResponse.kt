package com.project.mycode.welcome.signup

import com.google.gson.annotations.SerializedName

data class SignUpResponse (
    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("age")
    val age: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,
)