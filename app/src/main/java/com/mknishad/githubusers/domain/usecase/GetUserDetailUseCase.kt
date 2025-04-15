package com.mknishad.githubusers.domain.usecase

import com.mknishad.githubusers.common.Resource
import com.mknishad.githubusers.domain.model.UserDetail
import com.mknishad.githubusers.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(private val repository: GitHubRepository) {
  operator fun invoke(username: String): Flow<Resource<UserDetail>> = flow {
    try {
      emit(Resource.Loading())
      val result = repository.getUserByUsername(username)
      emit(Resource.Success(result))
    } catch (e: HttpException) {
      emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
    } catch (_: IOException) {
      emit(Resource.Error("Couldn't reach server. Please check internet connection"))
    }
  }
}
