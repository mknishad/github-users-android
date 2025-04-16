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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

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
  fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient =
    OkHttpClient.Builder().addInterceptor(interceptor).build()

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
