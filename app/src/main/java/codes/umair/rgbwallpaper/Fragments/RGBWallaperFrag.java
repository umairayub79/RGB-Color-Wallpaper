package codes.umair.rgbwallpaper.Fragments;


import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.IOException;
import java.util.ArrayList;

import codes.umair.rgbwallpaper.R;
import codes.umair.rgbwallpaper.Util;

/**
 * Created by Umair Ayub on 8/08/2019.
 */

public class RGBWallaperFrag extends Fragment {
    
    private SeekBar seekR;
    private SeekBar seekG;
    private SeekBar seekB;
	private TextView textCode;
	private FloatingActionButton fabRan,fabChng,fabSave;
	private ImageView previewV;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rgb_frag,container,false);
       // buttonRandom = (Button) view.findViewById(R.id.mainButtonRandom);
		fabRan = view.findViewById(R.id.mainButtonRandom);
		fabChng = view.findViewById(R.id.fab);
		fabSave = view.findViewById(R.id.fabsave);
		seekR = view.findViewById(R.id.mainSeekBarR);
		seekG = view.findViewById(R.id.mainSeekBarG);
		seekB = view.findViewById(R.id.mainSeekBarB);
		textCode = view.findViewById(R.id.mainTextViewCode);
		previewV = view.findViewById(R.id.previewV);
		GenerateRandom();

		seekR.setOnSeekBarChangeListener(new displayRGB());
        seekG.setOnSeekBarChangeListener(new displayRGB());
        seekB.setOnSeekBarChangeListener(new displayRGB());


		fabSave.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(final View p1)
				{
					// TODO: Implement this method
					
					final Bitmap bitmap = Util.createColorBitmap(getActivity(),Color.rgb(seekR.getProgress(),seekG.getProgress(),seekB.getProgress()));
					String rationale = "Please provide Storage permission to save Wallpapers.";
					String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
					
					Permissions.Options options = new Permissions.Options()
						.setRationaleDialogTitle("Info")
						.setSettingsDialogTitle("Warning");

					Permissions.check(getActivity(), permissions, rationale, options, new PermissionHandler() {
							@Override
							public void onGranted() {
								// do your task.
								Util.SaveBitmap(p1,bitmap);
							}

							@Override
							public void onDenied(Context context, ArrayList<String> deniedPermissions) {
								// permission denied, block the feature.
							}
						});
					
				}


			});
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
						Util.setWallpaper(getActivity(),Util.createColorBitmap(getActivity(),Color.rgb(seekR.getProgress(),seekG.getProgress(),seekB.getProgress())));
						Snackbar.make(p1,"Wallpaper Changed Successfully!",Snackbar.LENGTH_LONG).show();
					}catch (IOException e){
						e.printStackTrace();
						Snackbar.make(p1, "Unknown Error Occurred while changing wallpaper!", Snackbar.LENGTH_LONG).show();
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
