package sample.Entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Board;

public abstract class Entity {
    protected int locationX;
    protected int locationY;
    protected double y;
    protected double x;
    protected Board board;
    protected boolean isAllowMove;
    protected boolean detroyalbe;
    protected Image image;
    public int point;

    public Entity(int x,int y,Board board) {
        this.locationX = x;
        this.x = x;
        this.locationY = y;
        this.y = y;
        this.board = board;
        isAllowMove = true;
        detroyalbe = false;
        this.point = 10;
    }

    public boolean getDetroyalbe() {
        return detroyalbe;
    }

    public boolean getAllowMove(Object obj) {
        return isAllowMove;
    }


    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public double getCenterX() {
        return x+0.5;
    }
    public double getCenterY() {
        return y+0.5;
    }

    public boolean isCollide(double x,double y) {
        double distanceX = Math.abs(x+0.5f-this.getCenterX());
        double distanceY = Math.abs(y+0.5f-this.getCenterY());
        if (distanceX < 1 && distanceY < 1) return true;
        else return false;
    }

    public boolean isCollide(double x,double y,double fixed) {
        double distanceX = Math.abs(x+0.5f-this.getCenterX());
        double distanceY = Math.abs(y+0.5f-this.getCenterY());
        if (distanceX < 1-fixed && distanceY < 1-fixed) return true;
        else return false;
    }

    public abstract void render(GraphicsContext gameDraw);
    public abstract void update(double dt);
}
