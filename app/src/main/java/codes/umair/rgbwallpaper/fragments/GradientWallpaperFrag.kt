package codes.umair.rgbwallpaper.fragments

import android.Manifest
import android.app.ProgressDialog
import android.app.WallpaperManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import codes.umair.rgbwallpaper.R
import codes.umair.rgbwallpaper.Util
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 * Created by Umair Ayub on 8/08/2019.
 */

class GradientWallpaperFrag : Fragment() {

    private var v1: View? = null
    private var v2: View? = null
    private var v3: View? = null
    private var root: RelativeLayout? = null
    private var tvColorCode: TextView? = null
    private var fabRandom: FloatingActionButton? = null
    private var fabWall: FloatingActionButton? = null
    private var fabSave: FloatingActionButton? = null
    private var colors = ArrayList<String>()
    private var mDefaultColor = 0
    private var bitmap: Bitmap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.gradient_frag, container, false)

        fabRandom = view.findViewById(R.id.random)
        fabSave = view.findViewById(R.id.save)
        fabWall = view.findViewById(R.id.change)
        v1 = view.findViewById(R.id.v1)
        v2 = view.findViewById(R.id.v2)
        v3 = view.findViewById(R.id.v3)
        root = view.findViewById(R.id.root)
        tvColorCode = view.findViewById(R.id.mainTextViewCode)
        mDefaultColor = ContextCompat.getColor(activity!!, R.color.colorPrimary)
        v1?.setOnClickListener { openColorPicker("0") }
        v2?.setOnClickListener { openColorPicker("1") }
        v3?.setOnClickListener { openColorPicker("2") }

        generateRandom()
        v3?.setOnLongClickListener {
            if (colors.size == 3) {
                colors.removeAt(2)
                generateGradientWallpaper(colors)
                v3?.setBackgroundResource(R.drawable.ic_plus)
            } else {
                if (colors.size > 1) {
                    generateGradientWallpaper(colors)
                }
            }
            true
        }
        tvColorCode?.setOnClickListener {
            //Copy Color Code
            val myClipboard: ClipboardManager? = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val myClip = ClipData.newPlainText("text", tvColorCode?.text)
            myClipboard?.setPrimaryClip(myClip)
            Snackbar.make(it, "Copied to Clipboard!", Snackbar.LENGTH_LONG).show()

        }
        fabRandom?.setOnClickListener { generateRandom() }
        fabSave?.setOnClickListener { p1 ->
            val rationale = "Please provide Storage permission to save Wallpapers."
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val options = Permissions.Options()
                    .setRationaleDialogTitle("Info")
                    .setSettingsDialogTitle("Warning")
            Permissions.check(activity, permissions, rationale, options, object : PermissionHandler() {
                override fun onGranted() {
                    // do your task.
                    Util().saveBitmap(p1, bitmap!!)
                }

                override fun onDenied(context: Context, deniedPermissions: ArrayList<String>) {
                    // permission denied, block the feature.
                }
            })
        }
        fabWall?.setOnClickListener {
            if (colors.size > 1) {
                val runner = AsyncTaskRunner()
                runner.execute(bitmap)
                Toast.makeText(activity, colors.toString(), Toast.LENGTH_SHORT).show()
            } else if (colors.size < 2) {
                Toast.makeText(activity, "Select At least two colors", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun generateRandom() {
        colors.clear()
        val obj = Random()
        val randomNumber1 = obj.nextInt(0xffffff + 1)
        val randomNumber2 = obj.nextInt(0xffffff + 1)
        val randomNumber3 = obj.nextInt(0xffffff + 1)
        // format it as hexadecimal string and print.
        val colorCode1 = String.format("#%06x", randomNumber1)
        val colorCode2 = String.format("#%06x", randomNumber2)
        val colorCode3 = String.format("#%06x", randomNumber3)
        colors.add(colorCode1)
        colors.add(colorCode2)
        colors.add(colorCode3)
        generateGradientWallpaper(colors)
        v1!!.setBackgroundColor(Color.parseColor(colorCode1))
        v2!!.setBackgroundColor(Color.parseColor(colorCode2))
        v3!!.setBackgroundColor(Color.parseColor(colorCode3))


    }

    private fun updateColorCode() {
        tvColorCode?.text = ""
        for (i in colors) {
            tvColorCode?.text = "${tvColorCode?.text?.trim()}\n${i.trim()}"
        }
    }

    private fun openColorPicker(id: String) {
        val colorPicker = AmbilWarnaDialog(activity, mDefaultColor, object : OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                when (id) {
                    "0" -> {
                        val colorCode1 = String.format("#%06x", color)
                        v1!!.setBackgroundColor(Color.parseColor(colorCode1))
                        if (colors.size == 1) {
                            colors.add(colorCode1)
                            generateGradientWallpaper(colors)
                        } else {
                            if (colors.size >= 0) {
                                colors[0] = colorCode1
                                if (colors.size > 1) {
                                    generateGradientWallpaper(colors)
                                }
                            }
                        }
                    }
                    "1" -> {
                        val colorCode2 = String.format("#%06x", color)
                        if (colors.size == 0) {
                            Toast.makeText(activity, "Select a Color for slot 1 first", Toast.LENGTH_SHORT).show()
                        } else {
                            v2!!.setBackgroundColor(Color.parseColor(colorCode2))
                            if (colors.size == 1) {
                                colors.add(colorCode2)
                                generateGradientWallpaper(colors)
                            } else {
                                if (colors.size > 1) {
                                    colors[1] = colorCode2
                                    if (colors.size > 1) {
                                        generateGradientWallpaper(colors)
                                    }
                                }
                            }
                        }
                    }
                    "2" -> {
                        val colorCode3 = String.format("#%06x", color)
                        if (colors.size < 2) {
                            Toast.makeText(activity, "Select colors for first and second slot", Toast.LENGTH_SHORT).show()
                        } else {
                            v3!!.setBackgroundColor(Color.parseColor(colorCode3))
                            if (colors.size == 2) {
                                colors.add(colorCode3)
                                generateGradientWallpaper(colors)
                            } else {
                                if (colors.size > 2) {
                                    colors[2] = colorCode3
                                    if (colors.size > 1) {
                                        generateGradientWallpaper(colors)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })
        colorPicker.show()
    }

    private inner class AsyncTaskRunner : AsyncTask<Bitmap, Void?, Void?>() {
        override fun doInBackground(p1: Array<Bitmap>): Void? {
            return try {
                val wallpaperManager = WallpaperManager.getInstance(activity)
                wallpaperManager.setBitmap(p1[0])
                val fileOutputStream = FileOutputStream(File(activity!!.filesDir, "lastwlp.png"))
                p1[0].compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                null
            } catch (iOException: IOException) {
                iOException.printStackTrace()
                null
            }
        }

        var progressDialog: ProgressDialog? = null
        override fun onPreExecute() {
            progressDialog = ProgressDialog.show(activity,
                    "Just a Sec",
                    "Changing Wallpaper")
        }

        override fun onPostExecute(result: Void?) {
            progressDialog!!.dismiss()
        }
    }

    fun generateGradientWallpaper(arrayList: ArrayList<String>) {
        var n: Int
        val wallpaperManager = WallpaperManager.getInstance(this.activity)
        val n2 = wallpaperManager.desiredMinimumHeight
        val wallpaper = Bitmap.createBitmap(n2, n2, Bitmap.Config.ARGB_8888)
        val arrn = IntArray(arrayList.size)
        for (i in 0 until arrayList.size.also { n = it }) {
            arrn[i] = Color.parseColor(arrayList[i])
        }
        val paint = Paint()
        val linearGradient = LinearGradient(0.0f, 0.0f, n2.toFloat(), n2.toFloat(), arrn, null, Shader.TileMode.CLAMP)
        val canvas = Canvas(wallpaper)
        paint.shader = linearGradient
        canvas.drawRect(0.0f, 0.0f, n2.toFloat(), n2.toFloat(), paint)
        val background = BitmapDrawable(wallpaper)
        root!!.setBackgroundDrawable(background)
        bitmap = wallpaper
        updateColorCode()

    }
}