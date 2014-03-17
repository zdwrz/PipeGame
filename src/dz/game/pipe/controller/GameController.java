package dz.game.pipe.controller;

import android.graphics.Point;
import dz.game.pipe.util.Constants;
import dz.game.pipe.model.Pipe;

import java.util.*;

/**
 * Created by Dawei on 3/16/14.
 */
public class GameController {
    Queue<Pipe> comingPipesQueue;
    Map<Integer, Pipe> placedPipesMap;
    int gameStatus;

    public GameController(){

    }

    public void init() {
        comingPipesQueue = getRandomPipes(Constants.GAME_COL_NUMBER * Constants.GAME_ROW_NUMBER);
        placedPipesMap = populatePreSetPipes();
    }

    private Map<Integer, Pipe> populatePreSetPipes() {
        Map<Integer,Pipe> pMap = new HashMap<Integer, Pipe>();
        return pMap;
    }

    public void start(){
        init();
        gameStatus = Constants.STATUS_PENDING;
    }

    private Queue<Pipe> getRandomPipes(int numOfPipes){
        Queue<Pipe> queue = new LinkedList<Pipe>();
        Random random = new Random();
        while(numOfPipes-- > 0){
            int type = random.nextInt(11) + 1;
            Pipe p = new Pipe();
            p.setType(type);
            queue.add(p);
        }
        return queue;
    }

    public List<Pipe> getFirst4PipeInQueue(){
        List<Pipe> firstPipes = new ArrayList<Pipe>(4);
        Iterator<Pipe> ite = comingPipesQueue.iterator();
        for(int i = 0 ; i < 4 ; i ++){
            if(ite.hasNext()){
                firstPipes.add(ite.next());
            }
        }
        return firstPipes;
    }

    public Pipe getCurrentPipe(){
        return comingPipesQueue.peek();
    }

    public boolean placePipe(int x, int y){
        if(placedPipesMap.get(y*10 + x) != null){
            //position is occupied.
            return false;
        }else{
            Pipe p = comingPipesQueue.poll();
            p.setPosition(new Point(x,y));
            placedPipesMap.put(y*10+x, p);
            return true;
        }
    }

    /**
     *
     * @return game status
     *
     * Called when last pipe is placed or time's up.
     * Check all pipes and set connected.
     */
    public int checkGameOver(){
        if(comingPipesQueue.size() > 0){
            gameStatus = Constants.STATUS_PENDING;
        }else{
            this.checkGameStatus();
            this.gameStatus = Constants.STATUS_DEAD;
        }
        return this.gameStatus;
    }
    /**
     *  Should be called when time is over or all pipes are placed.
     *  check all pipes and set connected
     */
    public void checkGameStatus(){
        int midPoint = Constants.GAME_COL_NUMBER/2;
        Pipe startPipe = placedPipesMap.get(midPoint);
        if(startPipe == null||startPipe.getType() == 1 || startPipe.getType() == 4 || startPipe.getType() == 5 || startPipe.getType() == 9){
        }else{
            placedPipesMap.values();
        }
    }

    public List<Pipe> getConnectedPipes(){
        List<Pipe> connectedPipes = new ArrayList<Pipe>();
        if(this.gameStatus == Constants.STATUS_DEAD && placedPipesMap.size() > 0){
            Iterator<Pipe> ite = placedPipesMap.values().iterator();
            while(ite.hasNext()){
                Pipe p = ite.next();
                if(p.isConnected()){
                    connectedPipes.add(p);
                }
            }
        }
        return connectedPipes;
    }

}
