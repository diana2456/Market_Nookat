package nook.test.market_nookat.data.network

import nook.test.market_nookat.data.model.Directory
import nook.test.market_nookat.data.model.Reclemi
import nook.test.market_nookat.data.result.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

open class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.success(body)
                } else {
                    Resource.error("Response body is null", null, response.code())
                }
            } else {
                Resource.error(response.message(), null, response.code())
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

private fun <T> handleException(e: Exception): Resource<T> {
        return when (e) {
            is IOException -> {
                Resource.error("Network error: ${e.message}", null, null)
            }
            is SocketTimeoutException -> {
                Resource.error("Request timeout: ${e.message}", null, null)
            }
            else -> {
                Resource.error("Unexpected error: ${e.message}", null, null)
            }
        }
    }

    suspend fun <T> Call<T>.await(): Response<T> {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    continuation.resume(response)
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}


