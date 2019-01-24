package edu.rosehulman.cuiy1.photobucket

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.lang.Exception
import java.net.URL

class GetImageTask(private var imageConsumer: ImageConsumer) : AsyncTask<String, Void, Bitmap>() {
    override fun doInBackground(vararg urlStrings: String?): Bitmap? {
        try {
            val url = URL(urlStrings[0])
            Log.d(Constants.TAG, "Url: $url")
        } catch (e:Exception){
            Log.e(Constants.TAG,"Exception: URL ISSUE")
        }
        return try {
            val inStream = java.net.URL(urlStrings[0]).openStream()
            val bitmap = BitmapFactory.decodeStream(inStream)
            Log.d(Constants.TAG, "bitmap: ${bitmap.toString()}")
            bitmap
        } catch(e: Exception){
            Log.e(Constants.TAG,"Exception:" + e.toString())
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        imageConsumer.onImageLoaded(result)
    }

    interface ImageConsumer {
        fun onImageLoaded(pic: Bitmap?)
    }
}