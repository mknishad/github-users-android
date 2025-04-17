package com.mknishad.githubusers.domain.usecase

/**
 * This data class groups together all the use cases related to interacting with the GitHub API.  It serves as a convenient way to pass these related functionalities around the application.
 *
 * @property searchUsers The use case for searching GitHub users based on a query.
 * @property getUserDetail The use case for retrieving detailed information about a specific GitHub user.
 * @property getUserRepositories The use case for retrieving a list of repositories owned by a specific GitHub user.
 */
data class GitHubUseCases(
  val searchUsers: SearchUsersUseCase,
  val getUserDetail: GetUserDetailUseCase,
  val getUserRepositories: GetUserRepositoriesUseCase
)
