package dz.game.pipe.controller;

import dz.game.pipe.model.Point;
import dz.game.pipe.util.Constants;
import dz.game.pipe.model.Pipe;

import java.util.*;

/**
 * Created by Dawei on 3/16/14.
 */
public class GameController {
    Queue<Pipe> comingPipesQueue;
    Map<Integer, Pipe> placedPipesMap;
    public int gameStatus;

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
            checkMapForConnected();
        }
    }

    public void checkMapForConnected() {
        Pipe startP = placedPipesMap.get(Constants.GAME_COL_NUMBER/2);
        check(startP);

    }

    public void checkLeft(Pipe left){
        if(left != null && !left.isConnected()){
            if (left.getType() == 1 || left.getType() == 3
                    || left.getType() == 4 || left.getType() == 6
                    || left.getType() == 8 || left.getType() == 9
                    || left.getType() == 11) {
                left.setConnected(true);
                check(left);
            }
        }
    }
    public void checkRight(Pipe right){
        if(right != null && !right.isConnected()){
            if (right.getType() == 1 || right.getType() == 3
                    || right.getType() == 5 || right.getType() == 7
                    || right.getType() == 9 || right.getType() == 10
                    || right.getType() == 11) {
                right.setConnected(true);
                check(right);
            }
        }
    }
    public void checkUp(Pipe up){
        if(up != null && !up.isConnected()){
            if(up.getType() == 2 ||up.getType() == 3
                    ||up.getType() == 4 ||up.getType() == 5
                    ||up.getType() == 8 ||up.getType() == 9
                    ||up.getType() == 10 ){
                up.setConnected(true);
                check(up);
            }
        }
    }
    public void checkBottom(Pipe bottom){
        if(bottom != null && !bottom.isConnected()){
            if(bottom.getType() == 2 ||bottom.getType() == 3
                    ||bottom.getType() == 6 ||bottom.getType() == 7
                    ||bottom.getType() == 8 ||bottom.getType() == 10
                    ||bottom.getType() == 11){
                bottom.setConnected(true);
                check(bottom);
            }
        }
    }
    /** 11 types:
     *  - | + ┏ ┓ ┗ ┛ ┣ ┳  ┫  ┻
     *  1 2 3 4 5 6 7 8 9 10 11
     */
    public void check(Pipe p) {
        if(p == null){
            return;
        }
        if(p.getPosition().x == Constants.GAME_COL_NUMBER/2 && p.getPosition().y == 0){
            p.setConnected(true);
        }

        Pipe right = placedPipesMap.get(p.getPosition().y*10 + p.getPosition().x+1);
        Pipe left = placedPipesMap.get(p.getPosition().y*10 + p.getPosition().x-1);
        Pipe bottom = placedPipesMap.get((p.getPosition().y+1)*10 + p.getPosition().x);
        Pipe up = placedPipesMap.get((p.getPosition().y-1)*10 + p.getPosition().x);
        if(p.getType() == 1){
            checkLeft(left);
            checkRight(right);
        }else if(p.getType() == 2){
            checkUp(up);
            checkBottom(bottom);
        }else if(p.getType() == 3){
            checkRight(right);
            checkLeft(left);
            checkUp(up);
            checkBottom(bottom);
        }else if(p.getType() == 4){
            checkRight(right);
            checkBottom(bottom);
        }else if(p.getType() == 5){
            checkLeft(left);
            checkBottom(bottom);
        }else if(p.getType() == 6){
            checkUp(up);
            checkRight(right);
        }else if(p.getType() == 7){
            checkUp(up);
            checkLeft(left);
        }else if(p.getType() == 8){
            checkUp(up);
            checkRight(right);
            checkBottom(bottom);
        }else if(p.getType() == 9){
            checkLeft(left);
            checkRight(right);
            checkBottom(bottom);
        }else if(p.getType() == 10){
            checkUp(up);
            checkLeft(left);
            checkBottom(bottom);
        }else if(p.getType() == 11){
            checkUp(up);
            checkLeft(left);
            checkRight(right);
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

    public static void main(String[] s){
        GameController gc = new GameController();
        gc.start();

        gc.placePipe(0,0);
        gc.placePipe(0,1);
        gc.placePipe(0,2);
        gc.placePipe(0,3);
        gc.placePipe(0,4);
        gc.placePipe(1,1);
        gc.placePipe(1,2);
        gc.placePipe(1,3);
        gc.placePipe(1,4);
        gc.placePipe(1,0);
        gc.placePipe(2,1);
        gc.placePipe(2,2);
        gc.placePipe(2,3);
        gc.placePipe(2,4);
        gc.placePipe(2,0);
        gc.placePipe(3,1);
        gc.placePipe(3,2);
        gc.placePipe(3,3);
        gc.placePipe(3,4);
        gc.placePipe(3,0);
        gc.checkGameStatus();
        System.out.println(""+ gc.getCurrentPipe());
    }
}
