package org.scnsoft.fidekmp.data.api

import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse


sealed class ApiResult<T> {
    data class Success<T>(val dto: T) : ApiResult<T>()
    data class Failure<T>(val apiFailure: ApiFailure) : ApiResult<T>()
    data class Error<T>(val e: Throwable) : ApiResult<T>()
}

class ApiRequestSender(
     val errorParser: ErrorParser
) {
    suspend inline fun <reified T>  sendRequest(invoke: () -> Result<T>): ApiResult<T> {
            val result = invoke()
            return if (!result.isSuccess) ApiResult.Success(result.getOrNull()!!)
            else {
                val e = result.exceptionOrNull() ?: return  ApiResult.Error(Exception("unknown error"))
                Napier.e("sendRequest exception", e)
                val  r = errorParser.parse(e)
            if (r.code != -1) return ApiResult.Failure(r)
            return ApiResult.Error(e)
        }
    }

}
