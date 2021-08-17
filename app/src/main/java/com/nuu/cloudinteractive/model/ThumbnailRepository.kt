package com.nuu.cloudinteractive.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.nuu.cloudinteractive.ApiInterface
import java.io.DataOutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class ThumbnailRepository {
    companion object{
        private var instance: ThumbnailRepository? = null
        fun getInstance() = instance
            ?: ThumbnailRepository().also {
                instance = it
            }
    }

    fun getDataApi(urlString: String, params: HashMap<String, String>?, apiInterface: ApiInterface) {
        Thread {
            try {
                val mURL = URL(urlString)
                var sb = StringBuilder()

                with(mURL.openConnection() as HttpURLConnection) {
                    connectTimeout = 300000
                    connectTimeout = 300000
                    setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                    params?.run {
                        requestMethod = "POST"
                        val postData: ByteArray =
                            getQuery(params).toByteArray(StandardCharsets.UTF_8)
                        val wr = DataOutputStream(outputStream)
                        wr.write(postData)
                        wr.flush()
                    }

                    inputStream.bufferedReader().useLines {  //接收回傳結果
                        it.forEach {
                            sb.append(StringBuilder().apply {
                                append(it)
                            })
                        }
                    }
                }
                apiInterface.onSuccess(sb.toString())
            } catch (e: Exception) {
                apiInterface.onFailed(e.message)
            }
        }.start()
    }

    private fun getQuery(params: HashMap<String, String>): String {
        val result = java.lang.StringBuilder()
        var first = true
        for ((key, value) in params) {
            if (first) first = false else result.append("&")
            result.append(URLEncoder.encode(key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(value, "UTF-8"))
        }
        return result.toString()
    }


}