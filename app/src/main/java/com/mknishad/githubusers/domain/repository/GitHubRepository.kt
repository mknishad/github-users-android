package com.mknishad.githubusers.domain.repository

import com.mknishad.githubusers.data.remote.dto.RepositoryDto
import com.mknishad.githubusers.domain.model.Repository
import com.mknishad.githubusers.domain.model.SearchResult
import com.mknishad.githubusers.domain.model.UserDetail

interface GitHubRepository {
  suspend fun searchUsers(query: String): SearchResult
  suspend fun getUserByUsername(username: String): UserDetail
  suspend fun getUserRepositories(username: String): List<Repository>
}
