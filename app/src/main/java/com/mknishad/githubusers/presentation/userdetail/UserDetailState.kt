package com.mknishad.githubusers.presentation.userdetail

import com.mknishad.githubusers.domain.model.Repository
import com.mknishad.githubusers.domain.model.UserDetail

/**
 * Represents the UI state for the user detail screen.
 *
 * @property isLoading Indicates whether data is currently being loaded.  True if loading, false otherwise.
 * @property user Contains the detailed user information if available, otherwise null.
 * @property repositories A list of repositories associated with the user.  Empty if none or not yet loaded.
 * @property error  An error message if an error occurred during data loading. Empty string if no error.
 */
data class UserDetailState(
  val isLoading: Boolean = false,
  val user: UserDetail? = null,
  val repositories: List<Repository> = emptyList(),
  val error: String = ""
)
