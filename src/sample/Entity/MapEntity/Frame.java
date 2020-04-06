package sample.Entity.MapEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Board;
import sample.Entity.Enemy.Enemy;
import sample.Entity.Entity;
import sample.Entity.PlayerEntity.MainPlayer;
import sample.GameSetting;
import sample.Tools.RunLater;

import java.util.ArrayList;
import java.util.Collections;

public class Frame extends Entity {

    public static final int FRAMETYPE_UP = 0;
    public static final int FRAMETYPE_MID = 1;
    public static final int FRAMETYPE_LEFT = 2;
    public static final int FRAMETYPE_DOWN = 3;
    public static final int FRAMETYPE_RIGHT = 4;

    private int type;
    private double status;
    private double timeCount;
    public static Image[] imagesUp = {new Image(Frame.class.getResource("../../image/bombbang_up_1.png").toExternalForm())
                                    ,new Image(Frame.class.getResource("../../image/bombbang_up_2.png").toExternalForm())};
    public static Image[] imagesDown = {new Image(Frame.class.getResource("../../image/bombbang_down_1.png").toExternalForm())
            ,new Image(Frame.class.getResource("../../image/bombbang_down_2.png").toExternalForm())};
    public static Image[] imagesLeft = {new Image(Frame.class.getResource("../../image/bombbang_left_1.png").toExternalForm())
            ,new Image(Frame.class.getResource("../../image/bombbang_left_2.png").toExternalForm())};
    public static Image[] imagesRight = {new Image(Frame.class.getResource("../../image/bombbang_right_1.png").toExternalForm())
            ,new Image(Frame.class.getResource("../../image/bombbang_right_2.png").toExternalForm())};
    public static Image[] imagesMid = {new Image(Frame.class.getResource("../../image/bombbang_mid_2.png").toExternalForm())};


    public Frame(int i, int j, Board board, int type) {
        super(i,j,board);
        isAllowMove = true;
        this.type = type;
        status = 0;
        image = null;
        timeCount = 0;
    }



    @Override
    public void render(GraphicsContext gameDraw) {
        gameDraw.drawImage(image,locationX* GameSetting.blocks.get(),locationY*GameSetting.blocks.get(),GameSetting.blocks.get(),GameSetting.blocks.get());
    }

    @Override
    public void update(double dt) {
        status += dt*4;
        timeCount+=dt;
        if (type == Frame.FRAMETYPE_DOWN) image = imagesDown[(int)status%imagesDown.length];
        else if (type == Frame.FRAMETYPE_UP) image = imagesUp[(int)status%imagesUp.length];
        else if (type == Frame.FRAMETYPE_LEFT) image = imagesLeft[(int)status%imagesLeft.length];
        else if (type == Frame.FRAMETYPE_RIGHT) image = imagesRight[(int)status%imagesRight.length];
        else if (type == Frame.FRAMETYPE_MID) image = imagesMid[(int)status%imagesMid.length];

        if (timeCount>0.5f) {
            board.getRemoveList().add(new RunLater(locationX,locationY,this));
        }

        //detroy
        if (!board.getMap()[locationX][locationY].isEmpty()) {
            ArrayList<Entity> entities = new ArrayList<>(board.getMap()[locationX][locationY].getEntities());
            Collections.reverse(entities);
            for (Entity i : entities) {
                if (i instanceof Frame) continue;
                if (i.getDetroyalbe()) {
                    board.getRemoveList().add(new RunLater(locationX,locationY,i));
                    board.gameStatus.upScore(i.point);
                }
                break;
            }
        }

        //destroy main player
        if (timeCount < 0.2f) {    //to be real play
            if (board.getMainPlayer() != null) {
                double x = board.getMainPlayer().getX();
                double y = board.getMainPlayer().getY();
                if (this.isCollide(x,y,0.2)) {
                    System.out.println("Player died");
                    board.destroyMainPlayer();
                }
            }
        }

        for (Enemy i:board.getEnemyList()) {
            if (this.isCollide(i.getX(),i.getY(),0.15f)) {
                board.getRemoveEnemyList().add(new RunLater(0,0,i));
                board.gameStatus.upScore(i.point);
            }
        }
    }
}
