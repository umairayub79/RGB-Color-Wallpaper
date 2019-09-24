package codes.umair.rgbwallpaper;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import codes.umair.rgbwallpaper.Fragments.GradientWallpaperFrag;
import codes.umair.rgbwallpaper.Fragments.RGBWallaperFrag;
public class MainActivity extends AppCompatActivity {

    

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
	private Toolbar _toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(_toolbar);
		
		

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new RGBWallaperFrag(), "RGB Color");
        adapter.addFragment(new GradientWallpaperFrag(), "Gradient Color");
        viewPager.setAdapter(adapter);
    }

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		super.onBackPressed();
		finish();
	}

	
}
