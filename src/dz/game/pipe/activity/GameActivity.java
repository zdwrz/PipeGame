package dz.game.pipe.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;
import dz.game.pipe.R;
import dz.game.pipe.controller.GameController;
import dz.game.pipe.model.Pipe;
import dz.game.pipe.model.Point;
import dz.game.pipe.model.Rank;
import dz.game.pipe.util.Constants;
import dz.game.pipe.util.GameUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dawei on 3/16/14.
 */
public class GameActivity extends Activity {

    public static final int TOTAL_GAME_TIME = 180000;
    CountDownTimer gameTimer;
    GameController gameControl;
    Map<Point, ImageView> imageMap;
    Long secLeft = 0L;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        gameControl = new GameController();
        gameControl.start();
        buildGameArea();
        gameTimer = new CountDownTimer(TOTAL_GAME_TIME,1000) {
            TextView tv = (TextView) findViewById(R.id.timer_text);
            @Override
            public void onTick(long millisUntilFinished) {
                secLeft = millisUntilFinished/1000;
                tv.setText(secLeft/60 + ":" + secLeft%60);
            }

            @Override
            public void onFinish() {
                tv.setText("Time's Up!");
                gameControl.gameStatus = Constants.STATUS_DEAD;
                gameControl.checkGameStatus();
                updateScreenToFinish();
            }
        }.start();
    }

    private void buildGameArea() {
        imageMap = new HashMap<Point, ImageView>();
        Bitmap scaledBitmap = GameUtil.scalePipe(R.drawable.img_background,this);
        TableLayout table = (TableLayout)findViewById(R.id.game_area_table_id);
        for (int i = 0 ; i < Constants.GAME_ROW_NUMBER; i ++){
            TableRow row = new TableRow(this);
            for(int j = 0 ; j < Constants.GAME_COL_NUMBER ; j ++){
                ImageView image = new ImageView(this);
                image.setImageBitmap(scaledBitmap);
                image.setOnClickListener(clickListener);
                image.setContentDescription(j*10 + i +"");
                row.addView(image);
                imageMap.put(new Point(j,i),image);

            }
            table.addView(row);
        }
       updatePreview();
    }

    private void updatePreview() {
        List<Pipe> first4Pipes = gameControl.getFirst4PipeInQueue();
        int resID = getResources().getIdentifier("dz.game.pipe:drawable/img_background", null, null);
        if(first4Pipes!= null && first4Pipes.size() > 0){
            resID = getResources().getIdentifier("dz.game.pipe:drawable/img_pipe_" + first4Pipes.get(0).getType(), null, null);
        }
        ImageView preview1 = (ImageView)findViewById(R.id.preview_1);
        preview1.setImageBitmap(GameUtil.scalePipe(resID,this));
       // GameUtil.ImageViewAnimatedChangeForPreview(getApplicationContext(),preview1,GameUtil.scalePipe(resID,this));

        resID = getResources().getIdentifier("dz.game.pipe:drawable/img_background", null, null);
        if(first4Pipes!= null && first4Pipes.size() > 1){
            resID = getResources().getIdentifier("dz.game.pipe:drawable/img_pipe_" + first4Pipes.get(1).getType(), null, null);
        }
        ImageView preview2 = (ImageView)findViewById(R.id.preview_2);
        preview2.setImageBitmap(GameUtil.scalePipe(resID,this));
        //GameUtil.ImageViewAnimatedChangeForPreview(getApplicationContext(),preview2,GameUtil.scalePipe(resID,this));

        resID = getResources().getIdentifier("dz.game.pipe:drawable/img_background", null, null);
        if(first4Pipes!= null && first4Pipes.size() > 2){
            resID = getResources().getIdentifier("dz.game.pipe:drawable/img_pipe_" + first4Pipes.get(2).getType(), null, null);
        }
        ImageView preview3 = (ImageView)findViewById(R.id.preview_3);
        //preview3.setImageBitmap(GameUtil.scalePipe(resID,this));
        GameUtil.ImageViewAnimatedChangeForPreview(getApplicationContext(), preview3, GameUtil.scalePipe(resID, this));

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView iv = (ImageView)v;
            Pipe currentPipe = gameControl.getCurrentPipe();
            if(currentPipe != null && gameControl.gameStatus == Constants.STATUS_PENDING){
                int desc = Integer.parseInt(iv.getContentDescription().toString());
                if(gameControl.placePipe(desc/10, desc%10)){
                    int resID = getResources().getIdentifier("dz.game.pipe:drawable/img_pipe_" + currentPipe.getType(), null, null);
                    iv.setImageBitmap(GameUtil.scalePipe(resID, GameActivity.this));
                    updatePreview();
                    if(gameControl.checkGameOver() == Constants.STATUS_DEAD){
                        updateScreenToFinish();
                    }
                }
            }
        }
    };

    private void updateScreenToFinish() {
        gameTimer.cancel();
        for(Pipe cPipe : gameControl.getConnectedPipes()){
            Point p = cPipe.getPosition();
            ImageView image = imageMap.get(p);
            if(image != null){
                int resID = getResources().getIdentifier("dz.game.pipe:drawable/img_pipe_water_" + cPipe.getType(), null, null);
                GameUtil.ImageViewAnimatedChange(getApplicationContext(),image,GameUtil.scalePipe(resID,this));
            }
        }
        Long secUsed =  TOTAL_GAME_TIME - secLeft;
        int connectedPipeNum = gameControl.getConnectedPipes().size();
        saveRank(secUsed,connectedPipeNum);
    }

    private void saveRank(Long secUsed, int connectedPipeNum) {
        /*SharedPreferences pref = getApplicationContext().getSharedPreferences("dz.game.pipe", Context.MODE_PRIVATE);
        String key = "dz.game.pipe.rank";
        Map<Integer,Rank> rankMap = pref.getStringSet(key);*/
    }

    public void onClickReturn(View view) {
        this.finish();
    }

}