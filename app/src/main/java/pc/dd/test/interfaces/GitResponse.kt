package pc.dd.test.interfaces

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import pc.dd.test.data.UserResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GitResponse {
//    @GET("/search/users")
//    fun userListByFollowers( @Query("q") q: String, @Query("sort") sort: String,@Query("order") order: String): Object
//
//    @GET("/search/users?q=followers:>1000&sort=contributions&order=desc")
//    fun getAllUsersConstant(): Observable<UserResponse>
//
//    @GET("/users/{username}")
//    fun getUserByUsername(@Path("username") username:String) : Observable<User>
//

    @GET("/search/users")
    fun userListByFollowersAsync(
        @Query("q") q: String = "followers:>1000",
        @Query("sort") sort: String = "contributions",
        @Query("order") order: String = "desc"
    ): Deferred<Response<UserResponse>>

    companion object Factory {
        fun create(): GitResponse {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            return retrofit.create(GitResponse::class.java)
        }
    }
}