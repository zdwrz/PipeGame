package dz.game.pipe.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
        return Bitmap.createScaledBitmap(unscaledBitmap1, width, height, true);
    }

    public static void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        if (anim_out != null) {
            anim_out.setAnimationListener(new Animation.AnimationListener()
            {
                @Override public void onAnimationStart(Animation animation) {}
                @Override public void onAnimationRepeat(Animation animation) {}
                @Override public void onAnimationEnd(Animation animation)
                {
                    v.setImageBitmap(new_image);
                    if (anim_in != null) {
                        anim_in.setAnimationListener(new Animation.AnimationListener() {
                            @Override public void onAnimationStart(Animation animation) {}
                            @Override public void onAnimationRepeat(Animation animation) {}
                            @Override public void onAnimationEnd(Animation animation) {}
                        });
                    }
                    v.startAnimation(anim_in);
                }
            });
        }
        v.startAnimation(anim_out);
    }
    public static void ImageViewAnimatedChangeForPreview(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.slide_in_left);
        if (anim_out != null) {
            anim_out.setAnimationListener(new Animation.AnimationListener()
            {
                @Override public void onAnimationStart(Animation animation) {}
                @Override public void onAnimationRepeat(Animation animation) {}
                @Override public void onAnimationEnd(Animation animation)
                {
                    v.setImageBitmap(new_image);
                    if (anim_in != null) {
                        anim_in.setAnimationListener(new Animation.AnimationListener() {
                            @Override public void onAnimationStart(Animation animation) {}
                            @Override public void onAnimationRepeat(Animation animation) {}
                            @Override public void onAnimationEnd(Animation animation) {}
                        });
                    }
                    v.startAnimation(anim_in);
                }
            });
        }
        v.startAnimation(anim_out);
    }
}
