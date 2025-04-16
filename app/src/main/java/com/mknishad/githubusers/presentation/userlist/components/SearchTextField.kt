package com.mknishad.githubusers.presentation.userlist.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchTextField(
  text: String, hint: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier
) {
  OutlinedTextField(value = text, onValueChange = onValueChange, label = { Text(hint) })
}
