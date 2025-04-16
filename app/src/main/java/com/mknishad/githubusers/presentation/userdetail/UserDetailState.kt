package com.mknishad.githubusers.presentation.userdetail

import com.mknishad.githubusers.data.remote.dto.User
import com.mknishad.githubusers.domain.model.Repository
import com.mknishad.githubusers.domain.model.UserDetail

data class UserDetailState(
  val isLoading: Boolean = false,
  val user: UserDetail? = null,
  val repositories: List<Repository> = emptyList(),
  val error: String = ""
)
