package codes.umair.rgbwallpaper;

import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import codes.umair.rgbwallpaper.Fragments.*;
import codes.umair.rgbwallpaper.*;
public class MainActivity extends AppCompatActivity {

    

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
	private Toolbar _toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(_toolbar);
		
		

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
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
