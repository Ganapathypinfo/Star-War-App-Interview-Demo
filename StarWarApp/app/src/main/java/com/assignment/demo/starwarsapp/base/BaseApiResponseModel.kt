package com.assignment.demo.starwarsapp.base

data class BaseApiResponseModel<T>(
    val isSuccessful: Boolean = false,
    var apiResponseData: Any? = null,
    val apiException: Any? = null
)
