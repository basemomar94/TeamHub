package org.zayn.teamhub.core.utils.networkresultwrapper

import org.json.JSONException
import org.json.JSONObject
import org.zayn.teamhub.core.utils.Logger
import org.zayn.teamhub.core.utils.Logger.Companion.createLogger

private val log = Logger.createLogger("ErrorResponse")

data class ErrorResponse(val body: ErrorBody?, val code: Int?)

data class ErrorBody(
    val message: String?,
    val errors: Map<String, List<String>>?,
)

data class Error(val message: String, val errors: Map<String, String>)


fun mapErrorResponse(response: String): ErrorBody? {
    try {
        val json = JSONObject(response)
        log.d("mapErrorResponse: $json")
        val message = json.getString("message")
        val errors = mutableMapOf<String, List<String>>()
        try {
            val errorsJson = json.getJSONObject("errors")
            val keys = errorsJson.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                val value = errorsJson.getString(key)
                errors[key] = listOf(value)
            }
        } catch (e: Exception) {
            log.e("mapErrorResponse: ", e)
        }




        return ErrorBody(message, errors)
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    return null
}

fun mapErrorResponseWithResult(response: String): ErrorBody? {
    try {
        val json = JSONObject(response)
        val customerId = json.getJSONObject("data").getString("customer_id")
        log.d("mapErrorResponseWithResult: $json")
        val message = json.getString("message")
        val errors = mutableMapOf<String, List<String>>()
        errors["customerId"] = listOf(customerId)
        return ErrorBody(
            message = message,
            errors = errors
        )
    } catch (e: JSONException) {
        log.e("mapErrorResponseWithResult", e)
    }

    return null
}

