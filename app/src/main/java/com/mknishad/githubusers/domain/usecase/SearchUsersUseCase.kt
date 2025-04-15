package com.mknishad.githubusers.domain.usecase

import com.mknishad.githubusers.common.Resource
import com.mknishad.githubusers.domain.model.SearchResult
import com.mknishad.githubusers.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val repository: GitHubRepository) {
  operator fun invoke(query: String) : Flow<Resource<SearchResult>> = flow {
    try {
      emit(Resource.Loading())
      val result = repository.searchUsers(query)
      emit(Resource.Success(result))
    } catch (e: HttpException) {
      emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
    } catch (_: IOException) {
      emit(Resource.Error("Couldn't reach server. Please check internet connection"))
    }
  }
}
