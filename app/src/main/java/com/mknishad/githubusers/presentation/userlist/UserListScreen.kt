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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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

/**
 * Composable function that displays a list of users fetched from GitHub based on a search query.
 *
 * @param navController NavController used for navigating to the user detail screen.
 * @param viewModel UserListViewModel instance (provided by Hilt) that manages the state and data fetching for the user list.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
  navController: NavController, viewModel: UserListViewModel = hiltViewModel()
) {
  val state = viewModel.state.value
  val searchText = viewModel.searchText.value

  Scaffold(
    topBar = {
      TopAppBar(title = { Text(text = stringResource(R.string.app_name)) })
    }, modifier = Modifier.fillMaxSize()
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      Column {
        OutlinedTextField(
          value = searchText,
          onValueChange = { viewModel.onEvent(SearchUserEvent.EnteredText(it)) },
          label = { Text(stringResource(R.string.type_to_search_github_users)) },
          trailingIcon = {
            if (searchText.isNotBlank()) {
              Icon(
                Icons.Default.Clear, stringResource(R.string.clear), modifier = Modifier.clickable {
                  viewModel.onEvent(SearchUserEvent.ClearedText)
                }
              )
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
}