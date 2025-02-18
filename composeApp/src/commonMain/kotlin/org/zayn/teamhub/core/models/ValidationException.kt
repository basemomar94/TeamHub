package org.zayn.teamhub.core.models

data class ValidationException(val exception: ValidationError) : Exception()