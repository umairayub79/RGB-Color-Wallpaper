package codes.umair.rgbwallpaper

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import codes.umair.rgbwallpaper.fragments.GradientWallpaperFrag
import codes.umair.rgbwallpaper.fragments.RGBWallaperFrag
import com.google.android.material.tabs.TabLayout
import umairayub.madialog.MaDialog
import umairayub.madialog.MaDialogListener

class MainActivity : AppCompatActivity() {
    private var mSectionsPageAdapter: SectionsPageAdapter? = null
    private var mViewPager: ViewPager? = null
    private var _toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(_toolbar)
        mSectionsPageAdapter = SectionsPageAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container)
        setupViewPager(mViewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(mViewPager)
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = SectionsPageAdapter(supportFragmentManager)
        adapter.addFragment(RGBWallaperFrag(), "Plain Color")
        adapter.addFragment(GradientWallpaperFrag(), "Gradient Color")
        viewPager!!.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                share()
            }
            R.id.action_more_apps -> {
                moreApps()
            }
            R.id.action_rate_us -> {
                rateUs()
            }
            R.id.action_feedback -> {
                sendFeedback()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun rateUs() {
        MaDialog.Builder(this)
                .setImage(R.drawable.rating)
                .setTitle("Rate ${getString(R.string.app_name)}")
                .setMessage(getString(R.string.rateus_message))
                .setPositiveButtonText("Sure")
                .setPositiveButtonListener(object : MaDialogListener {
                    override fun onClick() {
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                        } catch (e: ActivityNotFoundException) {
                            e.printStackTrace()
                            Toast.makeText(this@MainActivity, e.localizedMessage, Toast.LENGTH_SHORT).show()
                            startActivity(
                                    Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")

                                    )
                            )
                        }
                    }
                })
                .setNegativeButtonText("Not Now")
                .setNegativeButtonListener(object : MaDialogListener {
                    override fun onClick() {

                    }
                })
                .build()
    }

    private fun moreApps() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Umair+Ayub")))
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            Toast.makeText(this@MainActivity, e.localizedMessage, Toast.LENGTH_SHORT).show()
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=pub:Umair+Ayub")))
        }
    }

    private fun sendFeedback() {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:umairayub79@gmail.com")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " Feedback")
        startActivity(Intent.createChooser(emailIntent, "Send Feedback!"))
    }

    private fun share() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey, Check out this Awesome ${getString(R.string.app_name)} App.\nThis app Lets you Generate Plain and Gradient Color Wallpapers.\nDownload Now : https://play.google.com/store/apps/details?id=$packageName"
        )
        startActivity(Intent.createChooser(shareIntent, "Share!"))
    }

}