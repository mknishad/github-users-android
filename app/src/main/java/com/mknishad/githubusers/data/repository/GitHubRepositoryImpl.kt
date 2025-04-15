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

class GitHubRepositoryImpl @Inject constructor(private val api: GitHubApi) : GitHubRepository {
  override suspend fun searchUsers(query: String): SearchResult {
    return api.searchUsers(query).toSearchResult()
  }

  override suspend fun getUserByUsername(username: String): UserDetail {
    return api.getUserDetails(username).toUserDetails()
  }

  override suspend fun getUserRepositories(username: String): List<Repository> {
    return api.getUserRepositories(username).map { it.toRepository() }
  }
}
