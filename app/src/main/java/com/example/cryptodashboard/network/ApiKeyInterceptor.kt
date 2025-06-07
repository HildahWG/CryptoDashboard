package com.example.cryptodashboard.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("x-cg-demo-api-key", "CG-9s65oapKcJNb7LNj8N5baWXT") // Replace with your key
            .build()
        return chain.proceed(request)
    }
}