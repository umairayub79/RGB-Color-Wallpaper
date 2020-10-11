package codes.umair.rgbwallpaper.fragments

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import codes.umair.rgbwallpaper.R
import codes.umair.rgbwallpaper.Util
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import java.io.IOException
import java.util.*

/**
 * Created by Umair Ayub on 8/08/2019.
 */
class RGBWallaperFrag : Fragment() {

    private var seekR: SeekBar? = null
    private var seekG: SeekBar? = null
    private var seekB: SeekBar? = null
    private var textCode: TextView? = null
    private var fabRan: FloatingActionButton? = null
    private var fabChng: FloatingActionButton? = null
    private var fabSave: FloatingActionButton? = null
    private var root: RelativeLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.rgb_frag, container, false)

        fabRan = view.findViewById(R.id.mainButtonRandom)
        fabChng = view.findViewById(R.id.fab)
        fabSave = view.findViewById(R.id.fabsave)
        seekR = view.findViewById(R.id.mainSeekBarR)
        seekG = view.findViewById(R.id.mainSeekBarG)
        seekB = view.findViewById(R.id.mainSeekBarB)
        textCode = view.findViewById(R.id.mainTextViewCode)
        root = view.findViewById(R.id.root)

        generateRandom()

        seekR?.setOnSeekBarChangeListener(DisplayRGB())
        seekG?.setOnSeekBarChangeListener(DisplayRGB())
        seekB?.setOnSeekBarChangeListener(DisplayRGB())

        textCode?.setOnClickListener {
            //Copy Color Code
            val myClipboard: ClipboardManager? = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
            val myClip = ClipData.newPlainText("text", textCode?.text)
            myClipboard?.setPrimaryClip(myClip)
            Snackbar.make(it, "Copied to Clipboard!", Snackbar.LENGTH_LONG).show()

        }

        fabSave?.setOnClickListener { it ->
            val bitmap = Util().createColorBitmap(activity!!, Color.rgb(seekR!!.progress, seekG!!.progress, seekB!!.progress))
            val rationale = "Please provide Storage permission to save Wallpapers."
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val options = Permissions.Options()
                    .setRationaleDialogTitle("Info")
                    .setSettingsDialogTitle("Warning")
            Permissions.check(activity, permissions, rationale, options, object : PermissionHandler() {
                override fun onGranted() {
                    // do your task.
                    Util().saveBitmap(it, bitmap)
                }

                override fun onDenied(context: Context, deniedPermissions: ArrayList<String>) {
                    // permission denied, block the feature.
                }
            })
        }
        fabRan?.setOnClickListener { generateRandom() }
        fabChng?.setOnClickListener { it ->
            try {
                Util().setWallpaper(activity, Util().createColorBitmap(activity!!, Color.rgb(seekR!!.progress, seekG!!.progress, seekB!!.progress)))
                Snackbar.make(view, "Wallpaper Changed Successfully!", Snackbar.LENGTH_LONG).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Snackbar.make(it, "Unknown Error Occurred while changing wallpaper!", Snackbar.LENGTH_LONG).show()
            }
        }
        return view
    }

    private fun generateRandom() {
        val randomR = Util().random()
        val randomG = Util().random()
        val randomB = Util().random()
        seekR!!.progress = randomR
        seekG!!.progress = randomG
        seekB!!.progress = randomB
        root!!.setBackgroundColor(Color.rgb(randomR, randomG, randomB))

    }

    override fun onResume() {
        super.onResume()
        DisplayRGB().onProgressChanged(seekR!!, 1, true)
    }

    inner class DisplayRGB : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            root!!.setBackgroundColor(Color.rgb(seekR!!.progress, seekG!!.progress, seekB!!.progress))
            var red = Integer.toHexString(seekR!!.progress)
            red = Util().zero(red)
            var green = Integer.toHexString(seekG!!.progress)
            green = Util().zero(green)
            var blue = Integer.toHexString(seekB!!.progress)
            blue = Util().zero(blue)
            val color = "#$red$green$blue"
            textCode!!.text = color
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }
}