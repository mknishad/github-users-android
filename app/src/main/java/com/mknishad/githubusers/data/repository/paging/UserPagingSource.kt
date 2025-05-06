package com.mknishad.githubusers.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mknishad.githubusers.data.remote.GitHubApi
import com.mknishad.githubusers.data.remote.dto.User
import com.mknishad.githubusers.data.remote.dto.toUser
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserPagingSource @Inject constructor(val api: GitHubApi) : PagingSource<Int, User>() {
  override fun getRefreshKey(state: PagingState<Int, User>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
    try {
      val nextPageNumber = params.key ?: 1
      val response = api.getUsers(since = nextPageNumber, perPage = params.loadSize)
      return LoadResult.Page(
        data = response.map { it.toUser() },
        prevKey = null,
        nextKey = response[response.size - 1].id
      )
    } catch (e: IOException) {
      return LoadResult.Error(e)
    } catch (e: HttpException) {
      return LoadResult.Error(e)
    }
  }
}