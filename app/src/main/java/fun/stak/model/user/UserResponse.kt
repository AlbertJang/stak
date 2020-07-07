package `fun`.stak.model.user

data class UserResponse(
    val code: Int,
    val message: String,
    val data: UserData
)