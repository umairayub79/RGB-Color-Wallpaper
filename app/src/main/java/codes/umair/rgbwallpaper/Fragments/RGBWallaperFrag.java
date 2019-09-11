package codes.umair.rgbwallpaper.Fragments;


import android.graphics.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import codes.umair.rgbwallpaper.*;
import android.support.design.widget.*;
import java.io.*;

/**
 * Created by Umair Ayub on 8/08/2019.
 */

public class RGBWallaperFrag extends Fragment {
    
    private SeekBar seekR;
    private SeekBar seekG;
    private SeekBar seekB;
	private TextView textCode;
	private FloatingActionButton fabRan,fabChng;
	private ImageView previewV;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rgb_frag,container,false);
       // buttonRandom = (Button) view.findViewById(R.id.mainButtonRandom);
		fabRan = (FloatingActionButton) view.findViewById(R.id.mainButtonRandom);
		fabChng = (FloatingActionButton) view.findViewById(R.id.fab);
        seekR = (SeekBar) view.findViewById(R.id.mainSeekBarR);
        seekG = (SeekBar) view.findViewById(R.id.mainSeekBarG);
        seekB = (SeekBar) view.findViewById(R.id.mainSeekBarB);
        textCode = (TextView) view.findViewById(R.id.mainTextViewCode);
        previewV = (ImageView) view.findViewById(R.id.previewV);
		GenerateRandom();

		seekR.setOnSeekBarChangeListener(new displayRGB());
        seekG.setOnSeekBarChangeListener(new displayRGB());
        seekB.setOnSeekBarChangeListener(new displayRGB());


		fabRan.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					GenerateRandom();

				}


			});
		fabChng.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					try{
						Util.setWallpaper(getActivity(),Color.rgb(seekR.getProgress(),seekG.getProgress(),seekB.getProgress()));
						Snackbar.make(p1,"Wallpaper Changed Successfully!",Snackbar.LENGTH_LONG).show();
					}catch (IOException e){
						e.printStackTrace();
					}
					
				}


			});
        return view;
    }
	
	
	public void GenerateRandom(){
		int randomR = Util.Random();
		int randomG = Util.Random();
		int randomB = Util.Random();

		seekR.setProgress(randomR);
		seekG.setProgress(randomG);
		seekB.setProgress(randomB);

		previewV.setBackgroundColor(Color.rgb(randomR, randomG, randomB));
	}


	@Override
    public void onResume(){
        super.onResume();

        new displayRGB().onProgressChanged(seekR, 1, true);

    }

	

	public class displayRGB implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            previewV.setBackgroundColor(Color.rgb(seekR.getProgress(), seekG.getProgress(), seekB.getProgress()));

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
