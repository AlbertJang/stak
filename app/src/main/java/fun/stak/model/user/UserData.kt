package `fun`.stak.model.user

import com.google.gson.annotations.SerializedName

data class UserData(
    val data: User,
    @SerializedName("login_token")
    val loginToken: String
)