package com.mknishad.githubusers.data.remote.dto

data class User(
  val name: String?,
  val avatarUrl: String,
  val id: Int,
  val login: String,
)