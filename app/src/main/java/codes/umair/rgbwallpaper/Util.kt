package codes.umair.rgbwallpaper

import android.app.Activity
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Util {
    fun random(): Int {
        return 0 + (Math.random() * 256).toInt()
    }

    fun zero(str: String): String {
        return if (str.length == 1) {
            "0$str"
        } else str
    }

    fun createColorBitmap(activity: Activity, n: Int): Bitmap {
        val wallpaperManager = WallpaperManager.getInstance(activity.applicationContext)
        val n2 = wallpaperManager.desiredMinimumHeight
        val bitmap = Bitmap.createBitmap(n2, n2, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(n)
        return bitmap
    }

    @Throws(IOException::class)
    fun setWallpaper(context: Context?, bitmap: Bitmap?) {
        val wallpaperManager = WallpaperManager.getInstance(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wallpaperManager.setBitmap(bitmap, null, true)
            return
        }
        wallpaperManager.setBitmap(bitmap)
    }

    fun saveBitmap(p1: View?, ImageBitmap: Bitmap) {
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/Color Wallpapers")
        myDir.mkdirs()
        val timeStamp = SimpleDateFormat("ddss").format(Date())
        val fname = "ColorWallpaper_$timeStamp.png"
        val file = File(myDir, fname)
        if (file.exists()) {
            file.delete()
        }
        try {
            val fileOut = FileOutputStream(file)
            ImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOut)
            fileOut.flush()
            fileOut.close()
            Snackbar.make(p1!!, "Wallpaper Saved Successfully👍🏼", Snackbar.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(p1!!, "An Error Occurred While Saving Wallpaper😕 ", Snackbar.LENGTH_LONG).show()
        }
    }
}