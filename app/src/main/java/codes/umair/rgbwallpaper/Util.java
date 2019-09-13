package codes.umair.rgbwallpaper;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.design.widget.*;
import android.view.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class Util {

    public static int Random(){
        return 0 + (int)(Math.random() * ((256)));
    }
    public static String zero(String str){
        if(str.length() == 1){
            return "0" + str;
        }
        return str;
    }
	
    public static Bitmap createColorBitmap(Activity activity,int n){
		
        WallpaperManager wallpaperManager = WallpaperManager.getInstance((activity.getApplicationContext()));
        int n2 = wallpaperManager.getDesiredMinimumHeight();
        Bitmap bitmap = Bitmap.createBitmap((int)n2, (int)n2, (Bitmap.Config)Bitmap.Config.ARGB_8888);
       
        bitmap.eraseColor(n);
        return bitmap;
    }
	
    public static void setWallpaper(Context context, Bitmap bitmap) throws IOException{
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wallpaperManager.setBitmap(bitmap,null,true);
            return;
        }
        wallpaperManager.setBitmap(bitmap);
    }

	
	public static void SaveBitmap(View p1,Bitmap ImageBitmap){
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/Color Wallpapers");
		myDir.mkdirs();
		
		String timeStamp = new SimpleDateFormat("ddss").format(new Date());
		String fname = "ColorWallpaper_" + timeStamp + ".png";
		
		File file = new File(myDir,fname);
		if(file.exists()){
			file.delete();
		}
			try
			{
				FileOutputStream fileOut = new FileOutputStream(file);
				ImageBitmap.compress(Bitmap.CompressFormat.PNG,100,fileOut);
				fileOut.flush();
				fileOut.close();
				Snackbar.make(p1,"Wallpaper Saved Successfully👍🏼",Snackbar.LENGTH_LONG).show();
				
			}catch (Exception e){
				e.printStackTrace();
				Snackbar.make(p1,"An Error Occurred While Saving Wallpaper😕 ",Snackbar.LENGTH_LONG).show();
			}
		}
}
