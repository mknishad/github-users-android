package com.mknishad.githubusers.presentation.userdetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mknishad.githubusers.common.Constants
import com.mknishad.githubusers.common.Resource
import com.mknishad.githubusers.domain.usecase.GitHubUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * ViewModel responsible for managing and providing user detail information.
 *
 * This ViewModel interacts with the [GitHubUseCases] to fetch user details and repositories
 * based on the provided username. It maintains the state of the user detail screen,
 * including loading status, user data, repositories, and potential errors.  The username
 * is retrieved from the [SavedStateHandle], allowing persistence across process death.
 *
 * @property gitHubUseCases The use cases for interacting with the GitHub API.
 * @property savedStateHandle Handle for accessing saved state data, used to retrieve the username.
 */
@HiltViewModel
class UserDetailViewModel @Inject constructor(
  private val gitHubUseCases: GitHubUseCases, savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val _state = mutableStateOf(UserDetailState())
  val state: State<UserDetailState> = _state

  init {
    savedStateHandle.get<String>(Constants.PARAM_USERNAME)?.let { username ->
      getUserDetail(username)
      getUserRepositories(username)
    }
  }

  private fun getUserDetail(username: String) {
    gitHubUseCases.getUserDetail(username).onEach { result ->
      when (result) {
        is Resource.Success -> {
          _state.value = UserDetailState(user = result.data)
        }

        is Resource.Error -> {
          _state.value = UserDetailState(
            error = result.message ?: "An unexpected error occurred"
          )
        }

        is Resource.Loading -> {
          _state.value = UserDetailState(isLoading = true)
        }
      }
    }.launchIn(viewModelScope)
  }

  private fun getUserRepositories(username: String) {
    gitHubUseCases.getUserRepositories(username).onEach { result ->
      when (result) {
        is Resource.Success -> {
          _state.value = state.value.copy(repositories = result.data ?: emptyList())
        }

        is Resource.Error -> {
          _state.value = state.value.copy(error = result.message ?: "An unexpected error occurred")
        }

        is Resource.Loading -> {
          _state.value = state.value.copy(isLoading = true)
        }
      }
    }.launchIn(viewModelScope)
  }
}
