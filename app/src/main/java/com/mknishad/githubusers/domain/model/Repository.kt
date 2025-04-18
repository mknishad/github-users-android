package com.mknishad.githubusers.domain.model

data class Repository(
  val name: String,
  val language: String?,
  val stargazersCount: Int?,
  val description: String?,
  val homepage: String?,
)