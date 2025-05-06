package com.mknishad.githubusers.data.remote

import com.mknishad.githubusers.data.remote.dto.RepositoryDto
import com.mknishad.githubusers.data.remote.dto.SearchResultDto
import com.mknishad.githubusers.data.remote.dto.UserDetailsDto
import com.mknishad.githubusers.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface defining the GitHub API endpoints used in the application.
 * This interface leverages Retrofit to define network requests to the GitHub API.
 */
interface GitHubApi {

  @GET("/users")
  suspend fun getUsers(@Query("since") since: Int, @Query("per_page") perPage: Int): List<UserDto>

  @GET("/search/users")
  suspend fun searchUsers(@Query("q") query: String): SearchResultDto

  @GET("/users/{username}")
  suspend fun getUserDetails(@Path("username") username: String?): UserDetailsDto

  @GET("/users/{username}/repos")
  suspend fun getUserRepositories(@Path("username") username: String?): List<RepositoryDto>
}
