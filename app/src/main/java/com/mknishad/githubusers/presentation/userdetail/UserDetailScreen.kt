package com.mknishad.githubusers.presentation.userdetail

import android.content.Context
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
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
  val context = LocalContext.current

  Box(modifier = modifier.fillMaxSize()) {
    state.user.let { user ->
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        item {
          AsyncImage(
            model = ImageRequest.Builder(context).data(user?.avatar_url).crossfade(true).build(),
            placeholder = painterResource(R.drawable.ic_placeholder),
            contentDescription = stringResource(R.string.user_icon),
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .size(100.dp)
              .clip(CircleShape)
          )
          Spacer(modifier = Modifier.height(24.dp))
          Text(
            text = user?.login ?: "",
            style = MaterialTheme.typography.bodySmall,
            fontStyle = FontStyle.Italic
          )
          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = user?.name ?: "",
            style = MaterialTheme.typography.headlineSmall,
          )
          Spacer(modifier = Modifier.height(16.dp))
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
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp)
          )
          Spacer(modifier = Modifier.height(8.dp))
        }
        items(state.repositories) { repository ->
          RepositoryListItem(
            repository = repository, onItemClick = {
              if (repository.homepage != null && repository.homepage.isNotBlank()) {
                openTab(context, repository.homepage)
              } else {
                Toast.makeText(
                  context,
                  context.getString(R.string.no_website_found_for_this_repository),
                  Toast.LENGTH_SHORT
                ).show()
              }
            }, modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 8.dp)
          )
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

// open url in custom chrome tabs
fun openTab(context: Context, url: String) {
  val packageName = "com.android.chrome"    // package name of chrome application.
  val customBuilder = CustomTabsIntent.Builder().build()
  customBuilder.intent.setPackage(packageName)
  customBuilder.launchUrl(context, url.toUri())
}