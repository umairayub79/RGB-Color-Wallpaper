package codes.umair.rgbwallpaper;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import java.io.*;

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
    public static Bitmap createColorBitmap(int n){
        Bitmap bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(n);
        return bitmap;
    }
    public static void setWallpaper(Context context, int n) throws IOException{
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        Bitmap bitmap = createColorBitmap(n);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wallpaperManager.setBitmap(bitmap,null,true);
            return;
        }
        wallpaperManager.setBitmap(bitmap);
    }
}
