package com.mknishad.githubusers.presentation.userlist.components

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mknishad.githubusers.R
import com.mknishad.githubusers.data.remote.dto.User

@Composable
fun UserListItem(user: User, onItemClick: (User) -> Unit, modifier: Modifier = Modifier) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onItemClick(user) }
      .padding(16.dp),
    horizontalArrangement = Arrangement.Start,
    verticalAlignment = Alignment.CenterVertically) {
    AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
        .data(user.avatar_url)
        .crossfade(true)
        .build(),
      placeholder = painterResource(R.drawable.ic_launcher_background),
      contentDescription = stringResource(R.string.user_icon),
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .size(50.dp)
        .clip(CircleShape)
        .background(Color.Cyan),
    )
    Spacer(modifier = Modifier.width(16.dp))
    Text(
      text = user.login,
      style = MaterialTheme.typography.headlineSmall,
      overflow = TextOverflow.Ellipsis
    )
  }
}