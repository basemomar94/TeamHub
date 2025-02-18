package org.zayn.teamhub.core.models

sealed class ValidationError {
    data class ApiError(val message: String?) : ValidationError()
    data object Required : ValidationError()
    data object NotValid : ValidationError()
}