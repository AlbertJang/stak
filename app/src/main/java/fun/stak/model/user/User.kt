package `fun`.stak.model.user

import com.google.gson.annotations.SerializedName

data class User(
    val email: String,
    val nick: String,
    @SerializedName("last_login")
    val lastLogin: String
)