package codes.umair.rgbwallpaper;

import android.graphics.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;

import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private FloatingActionButton fab;
	private Button buttonRandom;
    private SeekBar seekR;
    private SeekBar seekG;
    private SeekBar seekB;
	private TextView textCode;
	private LinearLayout root;
	
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
	
		
		buttonRandom = (Button) findViewById(R.id.mainButtonRandom);

        seekR = (SeekBar) findViewById(R.id.mainSeekBarR);
        seekG = (SeekBar) findViewById(R.id.mainSeekBarG);
        seekB = (SeekBar) findViewById(R.id.mainSeekBarB);
		root = (LinearLayout) findViewById(R.id.root);
        textCode = (TextView) findViewById(R.id.mainTextViewCode);
		fab = (FloatingActionButton)findViewById(R.id._fab);
		
		GenerateRandom();

		seekR.setOnSeekBarChangeListener(new displayRGB());
        seekG.setOnSeekBarChangeListener(new displayRGB());
        seekB.setOnSeekBarChangeListener(new displayRGB());


		buttonRandom.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					GenerateRandom();

				}


			});
		
		fab.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				try {
					Util.setWallpaper(MainActivity.this,Color.rgb(seekR.getProgress(),seekG.getProgress(),seekB.getProgress()));
					Snackbar.make(v, "Wallpaper Changed Successfully!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
    }
	
	
	
	public void GenerateRandom(){
		int randomR = Util.Random();
		int randomG = Util.Random();
		int randomB = Util.Random();

		seekR.setProgress(randomR);
		seekG.setProgress(randomG);
		seekB.setProgress(randomB);

		root.setBackgroundColor(Color.rgb(randomR, randomG, randomB));
	}


	@Override
    public void onResume(){
        super.onResume();

        new displayRGB().onProgressChanged(seekR, 1, true);

    }

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		super.onBackPressed();
		finish();
	}
	
	
	
	public class displayRGB implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            root.setBackgroundColor(Color.rgb(seekR.getProgress(), seekG.getProgress(), seekB.getProgress()));

            String red = Integer.toHexString(seekR.getProgress());
            red = Util.zero(red);

            String green = Integer.toHexString(seekG.getProgress());
            green = Util.zero(green);

            String blue = Integer.toHexString(seekB.getProgress());
            blue = Util.zero(blue);

            String color = "#" + red + green + blue;

            textCode.setText(color);
			textCode.setTextColor(Color.rgb(
									  seekR.getMax() - seekR.getProgress(),
									  seekG.getMax() - seekG.getProgress(),
									  seekB.getMax() - seekB.getProgress() ));


        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
	
	
}
