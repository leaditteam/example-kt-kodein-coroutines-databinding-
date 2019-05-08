package pc.dd.test.manager

import pc.dd.test.data.UserResponse
import pc.dd.test.interfaces.GitResponse
import pc.dd.test.util.safeApiCall
import java.io.IOException
import pc.dd.test.data.main.Result

class NetworkManager{
   private val gitResponse: GitResponse = GitResponse.create()
//
//    fun getUsersByFollowers( onUpdate: (UserResponse) -> Unit, onError: (Any) -> Unit ){
//       val resultObservable =
//           gitResponse.getAllUsersConstant()
//           .observeOn(AndroidSchedulers.mainThread())
//           .subscribeOn(Schedulers.io())
//           .subscribe ({
//                   result ->
//                Log.e(this.javaClass.name, result.toString())
//               onUpdate(result)
//           }, { error ->
//               onError(error)
//           })
//    }
//
//    fun getUserByNickName(nick: String, onUpdate: (User) -> Unit, onError: (Any) -> Unit){
//        val resultObservable =
//            gitResponse.getUserByUsername(nick)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe ({
//                        result ->
//                    Log.e(this.javaClass.name, result.toString())
//                    onUpdate(result)
//                }, { error ->
//                    onError(error)
//                })
//    }

    suspend fun searchUsers() = safeApiCall(
        call = {
            searchUserByFollowers()
        },
        errorMessage = "Error get user data"
    )

    private suspend fun searchUserByFollowers():  Result<UserResponse> {
        val response = gitResponse.userListByFollowersAsync().await()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return Result.Error(
            IOException("Error getting github data ${response.code()} ${response.message()}")
        )
    }
}