package org.zayn.teamhub.core.utils.networkresultwrapper

data class BaseResponse<T>(
    val data: T?,
    val message: String?
)