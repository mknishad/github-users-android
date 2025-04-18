package com.mknishad.githubusers.domain.repository

import com.mknishad.githubusers.domain.model.Repository
import com.mknishad.githubusers.domain.model.SearchResult
import com.mknishad.githubusers.domain.model.UserDetail

/**
 * Interface for interacting with the GitHub API to retrieve repository-related data.
 */
interface GitHubRepository {
  suspend fun searchUsers(query: String): SearchResult
  suspend fun getUserByUsername(username: String): UserDetail
  suspend fun getUserRepositories(username: String): List<Repository>
}
