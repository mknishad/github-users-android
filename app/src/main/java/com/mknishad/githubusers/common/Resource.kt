package com.mknishad.githubusers.common

/**
 * A sealed class representing the different states of a resource, such as a network request or data loading operation.
 *
 * This class provides a structured way to handle different outcomes: success, error, and loading.  It encapsulates
 * the data associated with the resource, if available, and an optional message for error or informational purposes.
 *
 * @param T The type of data the resource holds.
 * @param data The data associated with the resource.  Can be null, especially in cases of error or during loading when no initial data is available.
 * @param message An optional message providing context, such as an error message or a status update during loading.  Defaults to null.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
  class Success<T>(data: T) : Resource<T>(data)
  class Error<T>(data: T? = null, message: String) : Resource<T>(data, message)
  class Loading<T>(data: T? = null) : Resource<T>(data)
}