package com.mknishad.githubusers.di

import com.mknishad.githubusers.BuildConfig
import com.mknishad.githubusers.common.Constants
import com.mknishad.githubusers.data.remote.GitHubApi
import com.mknishad.githubusers.data.repository.GitHubRepositoryImpl
import com.mknishad.githubusers.domain.repository.GitHubRepository
import com.mknishad.githubusers.domain.usecase.GetUserDetailUseCase
import com.mknishad.githubusers.domain.usecase.GetUserRepositoriesUseCase
import com.mknishad.githubusers.domain.usecase.GitHubUseCases
import com.mknishad.githubusers.domain.usecase.SearchUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * AppModule
 *
 * This Hilt module provides application-wide dependencies, particularly related to networking
 * and the GitHub API integration.  It ensures that single instances of key components like
 * the OkHttpClient, Retrofit instance, and API repository are available throughout the app's lifecycle.
 *
 * Key functionalities provided:
 *  - **API Key Interception:** Injects the API key as a Bearer token in the Authorization header for all requests.
 *  - **HTTP Logging:** Enables logging of network requests and responses for debugging purposes.
 *  - **OkHttpClient Configuration:** Creates and configures the OkHttpClient with interceptors for authorization and logging.
 *  - **GitHub API Interface:** Builds the Retrofit client for interacting with the GitHub API, using Gson for JSON parsing and the configured OkHttpClient.
 *  - **GitHub Repository Implementation:** Provides the implementation of the GitHubRepository interface, utilizing the GitHub API service.
 *  - **GitHub Use Cases:**  Organizes and provides access to different use cases related to GitHub functionality (e.g., searching users, fetching user details).
 *
 *  Dependencies Provided:
 *   - `Interceptor` (authInterceptor): An interceptor to add the Authorization header.
 *   - `HttpLoggingInterceptor`: An interceptor to log HTTP request and response details.
 *   - `OkHttpClient`: A configured OkHttpClient for making network requests.
 *   - `GitHubApi`:  The Retrofit interface for the GitHub API.
 *   - `GitHubRepository`:  The implementation of the GitHub data repository.
 *   - `GitHubUseCases`: A container holding various GitHub-related use cases.
 *
 *  Scope: All provided dependencies are scoped to the SingletonComponent, ensuring a single instance exists throughout the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideAuthInterceptor() = Interceptor { chain ->
    val req = chain.request()
    val requestHeaders =
      req.newBuilder().addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}").build()
    chain.proceed(requestHeaders)
  }

  @Provides
  @Singleton
  fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(
    authInterceptor: Interceptor, loggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient =
    OkHttpClient.Builder().addInterceptor(authInterceptor).addInterceptor(loggingInterceptor)
      .build()

  @Provides
  @Singleton
  fun provideGitHubApi(okHttpClient: OkHttpClient): GitHubApi {
    return Retrofit.Builder().baseUrl(Constants.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
      .create(GitHubApi::class.java)
  }

  @Provides
  @Singleton
  fun provideGitHubRepository(api: GitHubApi): GitHubRepository = GitHubRepositoryImpl(api)

  @Provides
  @Singleton
  fun provideGitHubUseCases(repository: GitHubRepository): GitHubUseCases {
    return GitHubUseCases(
      searchUsers = SearchUsersUseCase(repository),
      getUserDetail = GetUserDetailUseCase(repository),
      getUserRepositories = GetUserRepositoriesUseCase(repository)
    )
  }
}
