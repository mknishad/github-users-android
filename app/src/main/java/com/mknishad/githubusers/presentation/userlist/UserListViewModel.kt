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
class UserListViewModel @Inject constructor(private val gitHubUseCases: GitHubUseCases) :
  ViewModel() {

  private val _state = mutableStateOf(UserListState())
  val state: State<UserListState> = _state

  private val _searchText = mutableStateOf("")
  val searchText: State<String> = _searchText

  fun getUsers(username: String) {
    _searchText.value = username
    if (searchText.value.isNotBlank()) {
      gitHubUseCases.searchUsers(searchText.value).onEach { result ->
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

  fun clearSearchText() {
    _searchText.value = ""
  }
}
