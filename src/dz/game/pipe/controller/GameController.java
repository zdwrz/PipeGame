package dz.game.pipe.controller;

import dz.game.pipe.model.Constants;
import dz.game.pipe.model.Pipe;

import java.util.List;
import java.util.Queue;

/**
 * Created by Dawei on 3/16/14.
 */
public class GameController {
    List<Pipe> placedPipesList;
    List<Pipe> comingPipesList;// queue ?
    Queue<Pipe> comingPipesQueue;

    public GameController(){

    }

    public void start(){

    }

    public void gameOver(){

    }

    public List<Pipe> getRandomPipes(int numOfPipes){
        List<Pipe> list = null;

        return list;
    }

    /**
     *  Always check whether the game is end.
     *  Should be called when every time the pipe is placed and time over.
     */
    private int checkGameStatus(){

        return Constants.STATUS_PENDING;
    }
}
