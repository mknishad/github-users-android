package com.mknishad.githubusers.domain.usecase

import androidx.paging.PagingData
import com.mknishad.githubusers.common.Resource
import com.mknishad.githubusers.data.remote.dto.User
import com.mknishad.githubusers.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow

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
class GetUsersUseCase(private val repository: GitHubRepository) {
  suspend fun invoke(): Flow<PagingData<User>> {
    return repository.getUsers()
  }
}
