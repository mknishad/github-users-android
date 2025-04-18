package com.mknishad.githubusers.data.remote.dto

import com.mknishad.githubusers.domain.model.SearchResult

data class SearchResultDto(
  val incomplete_results: Boolean, val items: List<User>, val total_count: Int
)

fun SearchResultDto.toSearchResult() = SearchResult(
  incomplete_results = incomplete_results, items = items, total_count = total_count
)
