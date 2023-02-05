package com.example.githubusers.util

class NetworkResult<out T>(val status: Status, val data: T? = null, val message: String? = null) {

    enum class Status { LOADING, SUCCESS, ERROR }

    companion object {

        fun <T> success(data: T?): NetworkResult<T> {
            return NetworkResult(Status.SUCCESS, data)
        }

        fun <T> error(error: String): NetworkResult<T> {
            return NetworkResult(Status.ERROR, message = error)
        }
    }
}