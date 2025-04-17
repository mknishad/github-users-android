package com.mknishad.githubusers.data.repository

import com.mknishad.githubusers.data.remote.GitHubApi
import com.mknishad.githubusers.data.remote.dto.toRepository
import com.mknishad.githubusers.data.remote.dto.toSearchResult
import com.mknishad.githubusers.data.remote.dto.toUserDetails
import com.mknishad.githubusers.domain.model.Repository
import com.mknishad.githubusers.domain.model.SearchResult
import com.mknishad.githubusers.domain.model.UserDetail
import com.mknishad.githubusers.domain.repository.GitHubRepository
import javax.inject.Inject

/**
 * Implementation of the [GitHubRepository] interface, responsible for interacting with the GitHub API.
 *
 * This class utilizes the [GitHubApi] to fetch data related to users and repositories.  It handles
 * the conversion of API responses to domain models and applies any necessary filtering.
 *
 * @property api The [GitHubApi] instance used for making API calls.  Injected via Dagger.
 */
class GitHubRepositoryImpl @Inject constructor(private val api: GitHubApi) : GitHubRepository {
  override suspend fun searchUsers(query: String): SearchResult {
    return api.searchUsers(query).toSearchResult()
  }

  override suspend fun getUserByUsername(username: String): UserDetail {
    return api.getUserDetails(username).toUserDetails()
  }

  override suspend fun getUserRepositories(username: String): List<Repository> {
    return api.getUserRepositories(username).filter { it.fork == false }.map { it.toRepository() }
  }
}
