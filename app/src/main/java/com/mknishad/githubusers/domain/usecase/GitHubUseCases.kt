package com.mknishad.githubusers.domain.usecase

data class GitHubUseCases(
  val searchUsers: SearchUsersUseCase,
  val getUserDetail: GetUserDetailUseCase,
  val getUserRepositories: GetUserRepositoriesUseCase
)
