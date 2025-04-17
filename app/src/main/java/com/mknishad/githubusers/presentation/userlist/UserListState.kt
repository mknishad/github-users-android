package com.mknishad.githubusers.presentation.userlist

import com.mknishad.githubusers.data.remote.dto.User

/**
 * Represents the state of the user list in a UI.
 *
 * This data class holds the information required to display a list of users,
 * including loading status, the user list itself, and any potential errors
 * encountered while fetching the data.
 *
 * @property isLoading  `true` if the user list is currently being loaded; `false` otherwise. Defaults to `false`.
 * @property users      The list of `User` objects. Defaults to an empty list.
 * @property error      An error message string if an error occurred during loading; empty string otherwise. Defaults to an empty string.
 */
data class UserListState(
  val isLoading: Boolean = false,
  val users: List<User> = emptyList(),
  val error: String = ""
)
