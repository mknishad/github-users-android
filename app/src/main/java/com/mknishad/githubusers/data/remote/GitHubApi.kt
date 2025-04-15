package com.mknishad.githubusers.data.remote

import com.mknishad.githubusers.data.remote.dto.SearchResultDto
import com.mknishad.githubusers.data.remote.dto.UserDetailsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

  @GET("/search/users")
  suspend fun searchUser(@Query("q") query: String): List<SearchResultDto>

  @GET("/users/{login}")
  fun getUserDetails(@Path("login") login: String?): Call<UserDetailsDto>
}
