package dz.game.pipe.model;

import android.graphics.Point;

/**
 * Created by Dawei on 3/16/14.
 */
public class Pipe {

    /** 11 types:
     *  - | + ┏ ┓ ┗ ┛ ┣ ┳  ┫  ┻
     *  1 2 3 4 5 6 7 8 9 10 11
     */
    private int type;
    private Point position;
    private boolean placed;
    private boolean checked;
    private boolean connected;

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
