package com.mknishad.githubusers.presentation.userlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mknishad.githubusers.common.Resource
import com.mknishad.githubusers.data.remote.dto.User
import com.mknishad.githubusers.domain.usecase.GitHubUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and interactions related to the user list screen.
 *
 * This ViewModel utilizes Hilt for dependency injection and interacts with [GitHubUseCases] to
 * fetch and display a list of users based on a search query. It maintains the current state of the
 * user list, including loading indicators, search results, and potential error messages.
 *
 * @property gitHubUseCases The use cases for interacting with the GitHub API. Injected via Hilt.
 */
@HiltViewModel
class UserListViewModel @Inject constructor(private val gitHubUseCases: GitHubUseCases) :
  ViewModel() {

  private val _state = mutableStateOf(UserListState())
  val state: State<UserListState> = _state

  private val _searchText = mutableStateOf("")
  val searchText: State<String> = _searchText

  private val _userState: MutableStateFlow<PagingData<User>> =
    MutableStateFlow(value = PagingData.empty())
  val userState: StateFlow<PagingData<User>> = _userState

  init {
    viewModelScope.launch {
      getUsers()
    }
  }

  private suspend fun getUsers() {
    gitHubUseCases.getUsers.invoke()
      .distinctUntilChanged()
      .cachedIn(viewModelScope)
      .collect {
        _userState.value = it
      }
  }

  fun onEvent(event: SearchUserEvent) {
    when (event) {
      is SearchUserEvent.EnteredText -> {
        searchUsers(event.value)
      }

      is SearchUserEvent.ClearedText -> {
        clearSearchText()
      }
    }
  }

  fun searchUsers(username: String) {
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
