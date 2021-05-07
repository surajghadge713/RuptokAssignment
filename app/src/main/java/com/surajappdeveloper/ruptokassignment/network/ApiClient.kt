package com.surajappdeveloper.ruptokassignment.network

import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.UnsupportedEncodingException
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {

        private const val TAG = "ApiClient"
        private const val CONNECT_TIMEOUT_MULTIPLIER = 1
        private const val DEFAULT_CONNECT_TIMEOUT_IN_SEC = 60 * CONNECT_TIMEOUT_MULTIPLIER
        private const val DEFAULT_WRITE_TIMEOUT_IN_SEC = 60 * CONNECT_TIMEOUT_MULTIPLIER
        private const val DEFAULT_READ_TIMEOUT_IN_SEC = 60 * CONNECT_TIMEOUT_MULTIPLIER

        private const val NO_OF_LOG_CHAR = 1000

        private var sRetrofitClient: Retrofit? = null
        private val sDispatcher: Dispatcher? = null

        fun getClient(): Retrofit? {
            if (sRetrofitClient == null) {
                sRetrofitClient = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClientBuilder().build())
                    .build()
            }
            return sRetrofitClient
        }

        private fun getOkHttpClientBuilder(): OkHttpClient.Builder {
            /*OkHttp client builder*/
            val oktHttpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(
                    (CONNECT_TIMEOUT_MULTIPLIER * DEFAULT_CONNECT_TIMEOUT_IN_SEC).toLong(),
                    TimeUnit.SECONDS
                )
                .writeTimeout(
                    (CONNECT_TIMEOUT_MULTIPLIER * DEFAULT_WRITE_TIMEOUT_IN_SEC).toLong(),
                    TimeUnit.SECONDS
                )
                .readTimeout(
                    (CONNECT_TIMEOUT_MULTIPLIER * DEFAULT_READ_TIMEOUT_IN_SEC).toLong(),
                    TimeUnit.SECONDS
                )
                .cookieJar(JavaNetCookieJar(getCookieManager())) /* Using okhttp3 cookie instead of java net cookie*/
            oktHttpClientBuilder.dispatcher(getDispatcher())

            oktHttpClientBuilder.addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                    .addHeader("Content-Type", "text/html")
                chain.proceed(builder.build())
            }

            oktHttpClientBuilder.addInterceptor(getHttpLoggingInterceptor())
            return oktHttpClientBuilder
        }



        private fun getHttpLoggingInterceptor(): Interceptor {
            val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    if (message.length > NO_OF_LOG_CHAR) {
                        for (noOfLogs in 0..message.length / NO_OF_LOG_CHAR) {
                            if (noOfLogs * NO_OF_LOG_CHAR + NO_OF_LOG_CHAR < message.length) {
                                Log.d(
                                    TAG,
                                    message.substring(
                                        noOfLogs * NO_OF_LOG_CHAR,
                                        noOfLogs * NO_OF_LOG_CHAR + NO_OF_LOG_CHAR
                                    )
                                )
                            } else {
                                Log.d(
                                    TAG,
                                    message.substring(noOfLogs * NO_OF_LOG_CHAR, message.length)
                                )
                            }
                        }
                    } else {
                        Log.d(TAG, message)
                    }
                }
            })
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return loggingInterceptor
        }

        private fun getCookieManager(): CookieManager {
            val cookieManager = CookieManager()
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
            return cookieManager
        }

        fun getDispatcher(): Dispatcher {
            return sDispatcher ?: Dispatcher()
        }

        private fun decode(url: String): String {
            return try {
                var prevURL = ""
                var decodeURL = url
                while (prevURL != decodeURL) {
                    prevURL = decodeURL
                    decodeURL = URLDecoder.decode(decodeURL, "UTF-8")
                }
                decodeURL
            } catch (e: UnsupportedEncodingException) {
                "Issue while decoding" + e.message
            }
        }
    }
}
