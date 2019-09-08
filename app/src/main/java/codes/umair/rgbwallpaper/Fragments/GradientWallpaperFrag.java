package codes.umair.rgbwallpaper.Fragments;


import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import codes.umair.rgbwallpaper.*;
import java.util.*;
import yuku.ambilwarna.*;

import android.support.v4.app.Fragment;
import codes.umair.rgbwallpaper.R;

/**
 * Created by Umair Ayub on 8/08/2019.
 */

public class GradientWallpaperFrag extends Fragment {
    
	View v1,v2,v3,previewV;
	Button btnRandom;
	Button btnWall;
	ArrayList<String>Colors = new ArrayList<>();
	int mDefaultColor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gradient_frag,container,false);
        
		btnRandom = (Button) view.findViewById(R.id.random);
		btnWall = (Button) view.findViewById(R.id.change);
		v1 = (View) view.findViewById(R.id.v1);
		v2 = (View) view.findViewById(R.id.v2);
		v3 = (View) view.findViewById(R.id.v3);
		previewV =(View) view.findViewById(R.id.preview);
			
		
		mDefaultColor = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
		
		v1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					openColorPicker("0");
					
				}
			
		});
		v2.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					openColorPicker("1");

				}

			});
		v3.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					openColorPicker("2");

				}

			});
		btnRandom.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					GenerateRandom();
					
				}

			
		});
		btnWall.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(!Colors.isEmpty()){
						setGradientWallpaper(Colors);
						//Toast.makeText(getActivity(),Colors.toString(),Toast.LENGTH_SHORT).show();
					}
				}

			
		});
        return view;
    }
	
	public void GenerateRandom(){
		Colors.clear();
		
		
		Random obj = new Random();
		int rand_num1 = obj. nextInt(0xffffff + 1);
		int rand_num2 = obj. nextInt(0xffffff + 1);
		int rand_num3 = obj. nextInt(0xffffff + 1);
		// format it as hexadecimal string and print.
		String colorCode1 = String. format("#%06x", rand_num1);
		String colorCode2 = String. format("#%06x", rand_num2);
		String colorCode3 = String. format("#%06x", rand_num3);
		
		Colors.add(colorCode1);
		Colors.add(colorCode2);
		Colors.add(colorCode3);
		
		v1.setBackgroundColor(Color.parseColor(colorCode1));
		v2.setBackgroundColor(Color.parseColor(colorCode2));
		v3.setBackgroundColor(Color.parseColor(colorCode3));
		
	}
	

    protected void setGradientWallpaper(ArrayList<String> arrayList) {
        int n;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance((Context)this.getActivity());
        int n2 = wallpaperManager.getDesiredMinimumHeight();
        Bitmap bitmap = Bitmap.createBitmap((int)n2, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        int[] arrn = new int[arrayList.size()];
        for (int i = 0; i < (n = arrayList.size()); ++i) {
            arrn[i] = Color.parseColor((String)((String)arrayList.get(i)));
        }
        Paint paint = new Paint();
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, (float)n2, (float)n2, arrn, null, Shader.TileMode.CLAMP);
        Canvas canvas = new Canvas(bitmap);
        paint.setShader((Shader)linearGradient);
        canvas.drawRect(0.0f, 0.0f, (float)n2, (float)n2, paint);
        Util.setBitmapAsWallpaper(wallpaperManager, getActivity(), bitmap);
        bitmap.recycle();
    }
	
	
	public void openColorPicker(final String id) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getActivity(), mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
				@Override
				public void onCancel(AmbilWarnaDialog dialog) {

				}

				@Override
				public void onOk(AmbilWarnaDialog dialog, int color) {
					switch (id) {
						case "0":
							String colorCode1 = String. format("#%06x", color);
							v1.setBackgroundColor(Color.parseColor(colorCode1));
							if(Colors.size() == 0){
								Colors.add(colorCode1);
							}
							if(Colors.size() > 0){
								Colors.set(0,colorCode1);
								Toast.makeText(getActivity(),colorCode1,Toast.LENGTH_SHORT).show();
							}
								
							
							break;
						case "1":
							String colorCode2 = String. format("#%06x", color);
							v2.setBackgroundColor(Color.parseColor(colorCode2));
							if(Colors.size() == 0){
								Toast.makeText(getActivity(),"Select.Color 1 first",Toast.LENGTH_SHORT).show();
							}
							if(Colors.size() == 1){
								Colors.add(colorCode2);
							}
							if(Colors.size() > 1){
								Colors.set(1,colorCode2);
								Toast.makeText(getActivity(),colorCode2,Toast.LENGTH_SHORT).show();
							}

							break;
						case "2":
							String colorCode3 = String. format("#%06x", color);
							v3.setBackgroundColor(Color.parseColor(colorCode3));
							if(Colors.size() == 1){
								Toast.makeText(getActivity(),"Select.Color 2 first",Toast.LENGTH_SHORT).show();
							}
							if(Colors.size() == 2){
								Colors.add(colorCode3);
							}
							if(Colors.size() > 2){
								Colors.set(2,colorCode3);
								Toast.makeText(getActivity(),colorCode3,Toast.LENGTH_SHORT).show();
							}
						
							break;
					} 
					
				}
			});
        colorPicker.show();
    }
}
