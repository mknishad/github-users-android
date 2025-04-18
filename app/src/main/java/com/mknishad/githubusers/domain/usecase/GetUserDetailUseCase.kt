package com.mknishad.githubusers.domain.usecase

import com.mknishad.githubusers.common.Resource
import com.mknishad.githubusers.domain.model.UserDetail
import com.mknishad.githubusers.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

/**
 * Use case for retrieving detailed information about a specific GitHub user.
 *
 * This class encapsulates the logic for fetching user details from the GitHub repository
 * and handling potential errors such as network issues or HTTP exceptions.  It returns
 * the result as a Flow emitting [Resource] objects to represent the different states:
 * loading, success, and error.
 *
 * @property repository The [GitHubRepository] used to interact with the GitHub API.
 * @constructor Injects the [GitHubRepository] dependency.
 */
class GetUserDetailUseCase(private val repository: GitHubRepository) {
  operator fun invoke(username: String): Flow<Resource<UserDetail>> = flow {
    try {
      emit(Resource.Loading())
      val result = repository.getUserByUsername(username)
      emit(Resource.Success(result))
    } catch (e: HttpException) {
      emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
    } catch (_: IOException) {
      emit(Resource.Error(message = "Couldn't reach server. Please check internet connection"))
    }
  }
}
