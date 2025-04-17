package com.mknishad.githubusers.domain.usecase

import com.mknishad.githubusers.common.Resource
import com.mknishad.githubusers.domain.model.SearchResult
import com.mknishad.githubusers.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use case for searching users on GitHub.
 *
 * This class encapsulates the logic for searching users based on a query string using the provided
 * [GitHubRepository]. It handles network calls, error handling (HttpException and IOException),
 * and emits the search results as a [Flow] of [Resource] objects, representing different states
 * (Loading, Success, Error).
 *
 * @property repository The [GitHubRepository] instance used to interact with the GitHub API.
 * @constructor Injects the [GitHubRepository] dependency.
 */
class SearchUsersUseCase @Inject constructor(private val repository: GitHubRepository) {
  operator fun invoke(query: String) : Flow<Resource<SearchResult>> = flow {
    try {
      emit(Resource.Loading())
      val result = repository.searchUsers(query)
      emit(Resource.Success(result))
    } catch (e: HttpException) {
      emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
    } catch (_: IOException) {
      emit(Resource.Error(message = "Couldn't reach server. Please check internet connection"))
    }
  }
}
