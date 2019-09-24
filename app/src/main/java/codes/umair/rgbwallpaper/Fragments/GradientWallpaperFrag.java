package codes.umair.rgbwallpaper.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import codes.umair.rgbwallpaper.R;
import codes.umair.rgbwallpaper.Util;
import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by Umair Ayub on 8/08/2019.
 */

public class GradientWallpaperFrag extends Fragment {
    
	View v1,v2,v3;
	ImageView previewV;
	FloatingActionButton fabRandom;
	FloatingActionButton fabWall;
	FloatingActionButton fabSave;
	ArrayList<String>Colors = new ArrayList<>();
	int mDefaultColor;
	Bitmap bitmap;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gradient_frag,container,false);

		fabRandom = view.findViewById(R.id.random);
		fabSave = view.findViewById(R.id.save);
		fabWall = view.findViewById(R.id.change);
		v1 = view.findViewById(R.id.v1);
		v2 = view.findViewById(R.id.v2);
		v3 = view.findViewById(R.id.v3);
		previewV = view.findViewById(R.id.preview);
			
		
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
		v3.setOnLongClickListener(new OnLongClickListener(){

				@Override
				public boolean onLongClick(View p1)
				{
					if(Colors.size() == 3){
							Colors.remove(2);
						GenerateGradientWallpaper(Colors);
							v3.setBackgroundResource(R.drawable.plus);
						}else{
							if(Colors.size() > 1){
								GenerateGradientWallpaper(Colors);
								
							}
						}
				return true;
				}
			});
			
		fabRandom.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					GenerateRandom();
					
				}

			
		});
		fabSave.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(final View p1)
				{
					// TODO: Implement this method
					//GenerateRandom();
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
		fabWall.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(Colors.size() > 1){
						AsyncTaskRunner runner = new AsyncTaskRunner();
						runner.execute(bitmap);
						Toast.makeText(getActivity(),Colors.toString(),Toast.LENGTH_SHORT).show();
					}else if(Colors.size() < 2){
						Toast.makeText(getActivity(),"Select At least two colors",Toast.LENGTH_SHORT).show();
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
		GenerateGradientWallpaper(Colors);
		v1.setBackgroundColor(Color.parseColor(colorCode1));
		v2.setBackgroundColor(Color.parseColor(colorCode2));
		v3.setBackgroundColor(Color.parseColor(colorCode3));
		
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
							previewV.setBackgroundColor(Color.parseColor(colorCode1));
							if(Colors.size() == 0){
								Colors.add(colorCode1);
								previewV.setBackgroundColor(Color.parseColor(colorCode1));
							}else{
							if(Colors.size() > 0){
								Colors.set(0,colorCode1);
								if(Colors.size() > 1){
								GenerateGradientWallpaper(Colors);
							}
							}
						}
							
							break;
						case "1":
							String colorCode2 = String. format("#%06x", color);
							
							if(Colors.size() == 0){
								Toast.makeText(getActivity(),"Select a Color for slot 1 first",Toast.LENGTH_SHORT).show();
							}else{
								v2.setBackgroundColor(Color.parseColor(colorCode2));
							if(Colors.size() == 1){
								Colors.add(colorCode2);
								GenerateGradientWallpaper(Colors);
							}else{
							if(Colors.size() > 1){
								Colors.set(1,colorCode2);
								if(Colors.size() > 1){
									GenerateGradientWallpaper(Colors);
								}
							
							}
						}
					}
							break;
						case "2":
							String colorCode3 = String. format("#%06x", color);
							
							if(Colors.size() < 2){
								Toast.makeText(getActivity(),"Select Colors for first and second slot",Toast.LENGTH_SHORT).show();
							}else{
								v3.setBackgroundColor(Color.parseColor(colorCode3));
							if(Colors.size() == 2){
								Colors.add(colorCode3);
								GenerateGradientWallpaper(Colors);
							}else{		
							if(Colors.size() > 2){
								Colors.set(2,colorCode3);
								if(Colors.size() > 1){
									GenerateGradientWallpaper(Colors);
								}
							}
						}
					}
							break;
					} 
					
				}
			});
        colorPicker.show();
    }
	protected void GenerateGradientWallpaper(ArrayList<String> arrayList) {
        int n;
		WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.getActivity());
        int n2 = wallpaperManager.getDesiredMinimumHeight();
		Bitmap wallpaper = Bitmap.createBitmap(n2, n2, Bitmap.Config.ARGB_8888);
        int[] arrn = new int[arrayList.size()];
        for (int i = 0; i < (n = arrayList.size()); ++i) {
			arrn[i] = Color.parseColor(arrayList.get(i));
        }
        Paint paint = new Paint();
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, (float)n2, (float)n2, arrn, null, Shader.TileMode.CLAMP);
        Canvas canvas = new Canvas(wallpaper);
		paint.setShader(linearGradient);
        canvas.drawRect(0.0f, 0.0f, (float)n2, (float)n2, paint);
		previewV.setImageBitmap(wallpaper);
		bitmap = wallpaper;
        
    }
	
	
	
	
	
	private class AsyncTaskRunner extends AsyncTask<Bitmap, Void, Void>
	{

		@Override
		protected Void doInBackground(Bitmap[] p1)
		{
			// TODO: Implement this method
			try {
				WallpaperManager wallpaperManager = WallpaperManager.getInstance(getActivity());
				wallpaperManager.setBitmap(p1[0]);
				FileOutputStream fileOutputStream = new FileOutputStream(new File(getActivity().getFilesDir(), "lastwlp.png"));
				p1[0].compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
				
				return null;
			}
			catch (IOException iOException) {
				iOException.printStackTrace();
				return null;
			}
		
		}

		ProgressDialog progressDialog;

		
        
		
        @Override
        protected void onPreExecute() {
           progressDialog = ProgressDialog.show(getActivity(),
											 "Just a Sec",
											 "Changing Wallpaper");
        }

		@Override
		protected void onPostExecute(Void result)
		{
			progressDialog.dismiss();
		}
		


       
    }
}
