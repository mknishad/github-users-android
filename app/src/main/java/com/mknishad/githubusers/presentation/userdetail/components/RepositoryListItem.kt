package com.mknishad.githubusers.presentation.userdetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mknishad.githubusers.R
import com.mknishad.githubusers.domain.model.Repository

@Composable
fun RepositoryListItem(
  repository: Repository, onItemClick: (Repository) -> Unit, modifier: Modifier = Modifier
) {
  Card(modifier = modifier, onClick = { onItemClick(repository) }) {
    Column(Modifier.padding(16.dp)) {
      Text(
        text = repository.name,
        style = MaterialTheme.typography.headlineSmall,
        overflow = TextOverflow.Ellipsis
      )
      Spacer(modifier = Modifier.height(8.dp))
      repository.description?.let {
        Text(
          text = it,
          style = MaterialTheme.typography.bodyMedium,
          overflow = TextOverflow.Ellipsis
        )
      }
      Spacer(Modifier.height(8.dp))
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          Icons.Default.Star,
          contentDescription = stringResource(R.string.star_icon),
          tint = Color.Yellow,
          modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(
          text = repository.stargazersCount.toString(),
          style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.width(16.dp))
        repository.language?.let {
          Card(
            shape = CircleShape,
            modifier = Modifier.size(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
          ) {}
          Spacer(Modifier.width(4.dp))
          Text(text = it, style = MaterialTheme.typography.bodySmall)
        }
      }
    }
  }
}