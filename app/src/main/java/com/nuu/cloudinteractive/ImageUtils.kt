package com.nuu.cloudinteractive

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import okhttp3.*
import java.io.*
import java.util.concurrent.Executors


class ImageUtils {

    companion object {
        @BindingAdapter("android:src")
        @JvmStatic
        fun loadImage(view: ImageView, urlString: String) {
            val fileName = urlString.substring(urlString.count() - 4, urlString.count())
            val filePath = view.context.getExternalFilesDir("picture")?.path + fileName + ".png"
            if (checkFile(filePath)){
                val bytes = File(filePath).readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                view.setImageBitmap(bitmap)
            }else {
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
                            writeFile(response!!.body().bytes(), filePath)
                            val bytes = File(filePath).readBytes()
                            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            handler.post {
                                view.setImageBitmap(bitmap)
                            }
                        }
                    })
                }
            }
        }

        private fun checkFile(fileName: String): Boolean {
            val mfile = File(fileName)
            return mfile.exists()
        }

        private fun writeFile(bytes: ByteArray?, path: String?): Boolean {
            val f = File(path)
            var fos: FileOutputStream? = null
            var result = false
            try {
                f.createNewFile()
                fos = FileOutputStream(f)
                fos.write(bytes)
                result = true
            } catch (e: IOException) {
            } finally {
            }
            return result
        }
    }
}