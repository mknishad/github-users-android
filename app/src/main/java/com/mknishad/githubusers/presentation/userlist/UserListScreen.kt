package com.mknishad.githubusers.presentation.userlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mknishad.githubusers.R
import com.mknishad.githubusers.presentation.userlist.components.UserListItem
import com.mknishad.githubusers.presentation.util.Screen

@Composable
fun UserListScreen(
  navController: NavController, viewModel: UserListViewModel = hiltViewModel()
) {
  val state = viewModel.state.value
  val searchText = viewModel.searchText.value

  Box(modifier = Modifier.fillMaxSize()) {
    Column {
      OutlinedTextField(
        value = searchText,
        onValueChange = { viewModel.getUsers(it) },
        label = { Text(stringResource(R.string.enter_github_username_to_search)) },
        trailingIcon = {
          if (searchText.isNotBlank()) {
            Icon(
              Icons.Default.Clear,
              stringResource(R.string.clear),
              modifier = Modifier.clickable {
                viewModel.clearSearchText()
              })
          }
        },
        modifier = Modifier
          .padding(horizontal = 16.dp, vertical = 8.dp)
          .fillMaxWidth()
      )
      LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.users) { user ->
          UserListItem(
            user = user, onItemClick = {
              navController.navigate(Screen.UserDetailScreen.route + "/${user.login}")
            }, modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 8.dp)
          )
        }
      }
    }
    if (state.error.isNotBlank()) {
      Text(
        text = state.error,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp)
          .align(Alignment.Center)
      )
    }
    if (state.isLoading) {
      CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
  }
}