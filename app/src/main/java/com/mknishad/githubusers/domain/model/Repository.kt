package com.mknishad.githubusers.domain.model

data class Repository(
  val name: String,
  val language: Any,
  val stargazersCount: Int,
  val description: String,
)