package com.example.obrestaurant.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    companion object {
        private const val API_KEY = "uonebancservceemultrS3cg8RaL30"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.toUri().toString()

        val action = when {
            url.endsWith("/get_item_list") -> "get_item_list"
            url.endsWith("/get_item_by_id") -> "get_item_by_id"
            url.endsWith("/get_item_by_filter") -> "get_item_by_filter"
            url.endsWith("/make_payment") -> "make_payment"
            else -> ""
        }

        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("X-Partner-API-Key", API_KEY)
            .addHeader("X-Forward-Proxy-Action", action)
            .build()

        return chain.proceed(modifiedRequest)
    }
}