package com.mknishad.githubusers.presentation.userdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mknishad.githubusers.R
import com.mknishad.githubusers.presentation.userdetail.components.RepositoryListItem

@Composable
fun UserDetailScreen(
  modifier: Modifier = Modifier, viewModel: UserDetailViewModel = hiltViewModel()
) {
  val state = viewModel.state.value

  Box(modifier = modifier.fillMaxSize()) {
    state.user.let { user ->
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        item {
          AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(user?.avatar_url)
              .crossfade(true).build(),
            //placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.user_icon),
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .padding(top = 16.dp)
              .size(100.dp)
              .clip(CircleShape)
          )
          Spacer(modifier = Modifier.height(24.dp))
          Text(
            text = user?.login ?: "",
            style = MaterialTheme.typography.bodySmall,
            fontStyle = FontStyle.Italic
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = user?.name ?: "",
            style = MaterialTheme.typography.headlineSmall,
          )
          Spacer(modifier = Modifier.height(8.dp))
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(
              text = stringResource(R.string.followers, user?.followers ?: 0),
              style = MaterialTheme.typography.bodyMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = stringResource(R.string.following, user?.following ?: 0),
              style = MaterialTheme.typography.bodyMedium,
              fontWeight = FontWeight.Bold
            )
          }
          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = stringResource(R.string.repositories),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp)
          )
          Spacer(modifier = Modifier.height(8.dp))
        }
        items(state.repositories) { repository ->
          RepositoryListItem(
            repository = repository,
            onItemClick = {},
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 8.dp)
          )
          //HorizontalDivider()
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