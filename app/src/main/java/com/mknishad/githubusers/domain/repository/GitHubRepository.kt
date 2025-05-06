package com.mknishad.githubusers.domain.repository

import androidx.paging.PagingData
import com.mknishad.githubusers.data.remote.dto.User
import com.mknishad.githubusers.domain.model.Repository
import com.mknishad.githubusers.domain.model.SearchResult
import com.mknishad.githubusers.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

/**
 * Interface for interacting with the GitHub API to retrieve repository-related data.
 */
interface GitHubRepository {
  suspend fun getUsers(): Flow<PagingData<User>>
  suspend fun searchUsers(query: String): SearchResult
  suspend fun getUserByUsername(username: String): UserDetail
  suspend fun getUserRepositories(username: String): List<Repository>
}
