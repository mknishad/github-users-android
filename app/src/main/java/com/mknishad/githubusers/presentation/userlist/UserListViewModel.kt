package com.mknishad.githubusers.presentation.userlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mknishad.githubusers.common.Resource
import com.mknishad.githubusers.domain.usecase.GitHubUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val gitHubUseCases: GitHubUseCases) : ViewModel() {

  private val _state = mutableStateOf(UserListState())
  val state: State<UserListState> = _state

  init {
    getUsers("m")
  }

  private fun getUsers(username: String) {
    gitHubUseCases.searchUsers(username).onEach { result ->
      when (result) {
        is Resource.Success -> {
          _state.value = UserListState(users = result.data?.items ?: emptyList())
        }
        is Resource.Error -> {
          _state.value = UserListState(
            error = result.message ?: "An unexpected error occurred"
          )
        }
        is Resource.Loading -> {
          _state.value = UserListState(isLoading = true)
        }
      }
    }.launchIn(viewModelScope)
  }
}
