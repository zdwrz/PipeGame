package dz.game.pipe.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import dz.game.pipe.R;

/**
 * Created by Dawei on 3/16/14.
 */
public class GameUtil {
    public static Bitmap scalePipe(int id, Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth()/5; // ((display.getWidth()*20)/100)
        int height = display.getHeight()/10;// ((display.getHeight()*30)/100)

        Bitmap unscaledBitmap1 = BitmapFactory.decodeResource(activity.getResources(), id);
        Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(unscaledBitmap1, width, height, true);
        return scaledBitmap1;
    }
}
