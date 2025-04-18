package com.mknishad.githubusers.data.remote.dto

data class Permissions(
  val admin: Boolean,
  val pull: Boolean,
  val push: Boolean
)