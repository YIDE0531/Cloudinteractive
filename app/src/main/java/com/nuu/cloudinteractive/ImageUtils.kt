package com.nuu.cloudinteractive

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import okhttp3.*
import java.io.IOException
import java.util.concurrent.Executors

class ImageUtils {

    companion object {
        @BindingAdapter("android:src")
        @JvmStatic
        fun loadImage(view: ImageView, urlString: String) {
            val executor = Executors.newSingleThreadExecutor()
            val handler = Handler(Looper.getMainLooper())
            executor.execute {
                val client = OkHttpClient()
                val request: Request = Request.Builder()
                    .url(urlString)
                    .build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                    }
                    override fun onResponse(call: Call?, response: Response?) {
                        response?.body()?.byteStream() // Read the data from the stream
                        handler.post {
                            var bitmap = BitmapFactory.decodeStream(response?.body()?.byteStream())
                            view.setImageBitmap(bitmap)
                        }
                    }
                })
            }
        }
    }
}