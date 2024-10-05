package android.kotlin.foodclub.api.retrofit

import android.kotlin.foodclub.api.authentication.API
import android.kotlin.foodclub.api.authentication.UserSignUpInformation
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://b9b1-49-43-169-93.ngrok-free.app/"

     val retrofitApi : API by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }
}