package dz.game.pipe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import com.example.Game001.R;

/**
 * Created by Dawei on 3/16/14.
 */
public class GameActivity extends Activity {

    CountDownTimer gameTimer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        gameTimer = new CountDownTimer(180000,1000) {
            TextView tv = (TextView) findViewById(R.id.timer_text);
            @Override
            public void onTick(long millisUntilFinished) {
                Long secPassed = millisUntilFinished/1000;
                tv.setText(secPassed/60 + ":" + secPassed%60);
            }

            @Override
            public void onFinish() {
                tv.setText("Time's Up!");
            }
        }.start();
    }

    public void onClickReturn(View view) {
        this.finish();
    }

}