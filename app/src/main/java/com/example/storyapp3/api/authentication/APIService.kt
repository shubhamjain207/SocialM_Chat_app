package android.kotlin.foodclub.api.authentication

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


data class UserSignUpInformation(
    val username: String,
    val email: String,
    val password: String,
)

data class UserSignInInformation(
    val username: String,
    val password: String,
)

data class UploadPhotoInformation(
    val username:String,
    val photoUrl:String
)

data class UserSignInResponse(
    val user:UserSignInInformation,
    val message:String
)

data class UploadPhotoResponse(
    val message:String
)


data class PublicUser(
    val username: String
)

data class PublicPhoto(
    val username:String,
    val photoUrl: String
)

data class AllUsers(
    val userList:List<PublicUser>,
)

data class AllPhotos(
    val photoList:List<PublicPhoto>,
)

data class MessageResponse(
    val sender:String,
    val receiver:String,
    val messagecontent:String,
    val messagetime:String,
    val messagetimeinmilli:String
)

data class MessageSent(
    val sender:String,
    val receiver:String,
    val messagecontent:String,
)




interface API {

    @POST("auth/register")
    suspend fun registerUser(
       @Body signUpInformation: UserSignUpInformation
    ):Response<UserSignUpInformation>

    @POST("auth/login")
    suspend fun loginUser(
        @Body signInInformation: UserSignInInformation
    ):Response<UserSignInResponse>

    @POST("user/uploadPhoto")
    suspend fun uploadPhoto(
        @Body uploadPhotoInformation: UploadPhotoInformation
    ):Response<UploadPhotoResponse>

    @GET("user/getAllUsers")
    suspend fun getAllUsers(
    ):Response<List<PublicUser>>

    @GET("user/getAllPhotos")
    suspend fun getAllPhotos(
    ):Response<List<PublicPhoto>>

    @POST("user/getPhotosOfUser")
    suspend fun getPhotosOfUsers(
        @Body userInformation: PublicUser
    ):Response<List<PublicPhoto>>

    @POST("user/sendMessage")
    suspend fun sendMessage(
        @Body messageSent: MessageSent
    ):Response<MessageResponse>

    @GET("user/getSendersAndReceiverMessages")
    suspend fun getAllChatMessages(
        @Query("sender") sender: String?,
        @Query("receiver") receiver: String?
    ):Response<MutableList<MessageResponse>>




}
