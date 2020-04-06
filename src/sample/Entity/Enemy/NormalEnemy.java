package sample.Entity.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Board;
import sample.GameSetting;

import java.util.Random;

public class NormalEnemy extends Enemy {

    private static Image imageLeft = new Image(NormalEnemy.class.getResource("../../image/boss_left.png").toExternalForm());
    private static Image imageRight = new Image(NormalEnemy.class.getResource("../../image/boss_right.png").toExternalForm());
    private static Image imageDown = new Image(NormalEnemy.class.getResource("../../image/boss_down.png").toExternalForm());
    private static Image imageUp = new Image(NormalEnemy.class.getResource("../../image/boss_up.png").toExternalForm());

    public NormalEnemy(int i, int j, Board board) {
        super(i, j, board);
        speed = Enemy.ENEMYNORMAL_SPEED;
        image = imageDown;
        this.point = 40;
    }

    public boolean getAllowMove(Object obj) {
        if (obj instanceof Enemy) return false;
        return isAllowMove;
    }


    @Override
    protected void handleNextMove() {
        int[] array = new int[4];
        int count = 0;
        // isTopAvailable ?
        if (locationY - 1 >= 0) {
            if (board.getMap()[locationX][locationY-1].isEmpty()) {
                array[count++] = GameSetting.DIRECTION_UP;
            } else if (board.getMap()[locationX][locationY-1].getEntity().getAllowMove(this)) {
                array[count++] = GameSetting.DIRECTION_UP;
            }
        }
        //isBotAvailable ?
        if (locationY + 1 < GameSetting.GAMESCALEMAP) {
            if (board.getMap()[locationX][locationY+1].isEmpty()) {
                array[count++] = GameSetting.DIRECTION_DOWN;
            } else if (board.getMap()[locationX][locationY+1].getEntity().getAllowMove(this)) {
                array[count++] = GameSetting.DIRECTION_DOWN;
            }
        }
        //isLeftAvailable ?
        if (locationX - 1 >= 0) {
            if (board.getMap()[locationX-1][locationY].isEmpty()) {
                array[count++] = GameSetting.DIRECTION_LEFT;
            } else if (board.getMap()[locationX-1][locationY].getEntity().getAllowMove(this)) {
                array[count++] = GameSetting.DIRECTION_LEFT;
            }
        }
        //isRightAvailable ?
        if (locationX +1 < GameSetting.GAMESCALEMAP) {
            if (board.getMap()[locationX+1][locationY].isEmpty()) {
                array[count++] = GameSetting.DIRECTION_RIGHT;
            } else if (board.getMap()[locationX+1][locationY].getEntity().getAllowMove(this)) {
                array[count++] = GameSetting.DIRECTION_RIGHT;
            }
        }
        if (count == 0) {
            direction = GameSetting.DIRECTION_NONE;
            return;
        }
        int random = new Random().nextInt(count);

        direction = array[random];
        switch (direction) {
            case GameSetting.DIRECTION_DOWN : image=imageDown; break;
            case GameSetting.DIRECTION_UP : image=imageUp; break;
            case GameSetting.DIRECTION_LEFT : image=imageLeft; break;
            case GameSetting.DIRECTION_RIGHT : image=imageRight; break;
            case GameSetting.DIRECTION_NONE: image=imageDown; break;
        }
    }

    @Override
    public void render(GraphicsContext gameDraw) {
        gameDraw.drawImage(image,x*GameSetting.blocks.get(),y*GameSetting.blocks.get(),GameSetting.blocks.get(),GameSetting.blocks.get());
    }

    public void firstLoadDirection() {
        handleNextMove();
    }

    @Override
    public void update(double dt) {
        move(dt);

        if (Math.abs(lengthStreetPass-1)<=0.05f) {
            locationY = (int)(y+0.5);
            locationX = (int)(x+0.5);
            x=locationX;
            y=locationY;
            lengthStreetPass=0;
            handleNextMove();
        }

        if (board.getMainPlayer() != null) {
            if (this.isCollide(board.getMainPlayer().getX(),board.getMainPlayer().getY(),0.15f)) {
                board.destroyMainPlayer();
            }
        }

    }


}
