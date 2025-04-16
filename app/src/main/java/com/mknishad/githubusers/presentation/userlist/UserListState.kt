package com.mknishad.githubusers.presentation.userlist

import com.mknishad.githubusers.data.remote.dto.User

data class UserListState(
  val isLoading: Boolean = false,
  val users: List<User> = emptyList(),
  val error: String = ""
)
