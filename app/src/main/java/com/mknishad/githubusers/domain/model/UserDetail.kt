package com.mknishad.githubusers.domain.model

data class UserDetail(
    val avatar_url: String,
    val followers: Int,
    val following: Int,
    val login: String,
    val name: String,
    val public_repos: Int,
    val repos_url: String,
    val url: String
)