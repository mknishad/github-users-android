package com.mknishad.githubusers.domain.model

import com.mknishad.githubusers.data.remote.dto.User

data class SearchResult(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)

