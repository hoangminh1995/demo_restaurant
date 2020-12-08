package com.demorestaurant.data

import android.content.Context
import com.demorestaurant.R
import com.demorestaurant.utils.InternetUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class NetworkConnectionInterceptor(private val mContext: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!InternetUtil.isConnectedToInternet(mContext)) {
            throw NoConnectivityException(mContext.getString(R.string.offline_msg))
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    class NoConnectivityException(message: String) : IOException(message)
}