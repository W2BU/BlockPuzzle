package com.example.blockpuzzle.common

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException

fun getBitmapFromAsset(context: Context?, pathToFile: String): Bitmap? {
    val assetManager: AssetManager = context!!.assets
    var bitmap: Bitmap? = null
    try {
        val inStream = assetManager.open(pathToFile)
        bitmap = BitmapFactory.decodeStream(inStream)
    } catch (e: IOException) {}
    return bitmap
}