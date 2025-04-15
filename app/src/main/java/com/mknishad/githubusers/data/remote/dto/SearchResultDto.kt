package com.mknishad.githubusers.data.remote.dto

data class SearchResultDto(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)