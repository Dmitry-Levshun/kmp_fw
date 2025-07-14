package org.scnsoft.fidekmp.data.api

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

data class ApiFailure(
    val code: Int,
    val message: String,
    val cause: Throwable? = null
)

class ErrorParser(
    private val json: Json = Json { ignoreUnknownKeys = true }
) {

    suspend fun parse(throwable: Throwable): ApiFailure {
        return when (throwable) {
            is ResponseException -> {
                val code = throwable.response.status.value
                val errorMessage = tryParseMessage(throwable)
                ApiFailure(code, errorMessage, throwable)
            }
            else -> ApiFailure(-1, "Unexpected error", throwable)
        }
    }

    private suspend fun tryParseMessage(exception: ResponseException): String {
        return try {
            val text = exception.response.bodyAsText()
            val parsed = json.decodeFromString<ServerErrorBody>(text)
            parsed.message
        } catch (_: Exception) {
            exception.message ?: "Unknown error"
        }
    }

    @Serializable
    private data class ServerErrorBody(val message: String)
}
/*
val result = try {
    val response = client.get("...")
    Result.success(response)
} catch (e: Throwable) {
    val apiError = errorParser.parse(e)
    Result.failure(apiError)
}

 */