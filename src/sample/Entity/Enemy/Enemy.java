package sample.Entity.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Board;
import sample.Entity.Entity;
import sample.GameSetting;

public abstract class Enemy extends Entity {


    protected static final double ENEMYNORMAL_SPEED = 1.0f;
    protected static final double ENEMYTANKER_SPEED = 3.0f;

    protected double lengthStreetPass;
    protected double speed;
    protected int direction;


    public Enemy(int i, int j, Board board) {
        super(i,j,board);
        isAllowMove = true;
        lengthStreetPass=0;
        direction = GameSetting.DIRECTION_NONE;
    }

    public abstract void firstLoadDirection();

    protected abstract void handleNextMove();

    protected void move(double dt) {
        switch (direction) {
         case GameSetting.DIRECTION_DOWN : y+=dt*speed;  lengthStreetPass+=dt*speed; break;
         case GameSetting.DIRECTION_UP : y-=dt*speed; lengthStreetPass+=dt*speed; break;
         case GameSetting.DIRECTION_LEFT : x-=dt*speed; lengthStreetPass+=dt*speed; break;
         case GameSetting.DIRECTION_RIGHT : x+=dt*speed; lengthStreetPass+=dt*speed; break;
         case GameSetting.DIRECTION_NONE: break;
         }

    }
}
