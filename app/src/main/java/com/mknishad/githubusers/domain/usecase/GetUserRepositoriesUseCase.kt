package com.mknishad.githubusers.domain.usecase

import com.mknishad.githubusers.common.Resource
import com.mknishad.githubusers.domain.model.Repository
import com.mknishad.githubusers.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

/**
 * Use case for retrieving a list of repositories for a given GitHub user.
 *
 * This class encapsulates the logic for fetching a user's repositories from the GitHub API
 * using the provided [GitHubRepository]. It handles potential errors during the API call, such as
 * network issues or HTTP errors, and emits a [Flow] of [Resource] objects representing the
 * different states of the operation (Loading, Success, Error).
 *
 * @param repository The [GitHubRepository] instance used to access the GitHub API.  Injected via Hilt.
 */
class GetUserRepositoriesUseCase(private val repository: GitHubRepository) {
  operator fun invoke(username: String): Flow<Resource<List<Repository>>> = flow {
    try {
      emit(Resource.Loading())
      val result = repository.getUserRepositories(username)
      emit(Resource.Success(result))
    } catch (e: HttpException) {
      emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
    } catch (_: IOException) {
      emit(Resource.Error(message = "Couldn't reach server. Please check internet connection"))
    }
  }
}
