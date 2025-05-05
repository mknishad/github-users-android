package com.mknishad.githubusers.presentation.userlist

sealed class SearchUserEvent {
  data class EnteredText(val value: String): SearchUserEvent()
  object ClearedText: SearchUserEvent()
}