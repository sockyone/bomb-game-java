package sample.Entity.MapEntity;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Board;
import sample.Entity.Entity;
import sample.Entity.PlayerEntity.MainPlayer;
import sample.GameSetting;
import sample.SoundTools;
import sample.Tools.RunLater;

public class Bomb extends Entity {

    private static Image[] images = {new Image(Bomb.class.getResource("../../image/boom0.png").toExternalForm())
            ,new Image(Bomb.class.getResource("../../image/boom1.png").toExternalForm())
            ,new Image(Bomb.class.getResource("../../image/boom2.png").toExternalForm())
            ,new Image(Bomb.class.getResource("../../image/boom3.png").toExternalForm())
            ,new Image(Bomb.class.getResource("../../image/boom4.png").toExternalForm())
            ,new Image(Bomb.class.getResource("../../image/boom5.png").toExternalForm())
            ,new Image(Bomb.class.getResource("../../image/boom6.png").toExternalForm())
            ,new Image(Bomb.class.getResource("../../image/boom7.png").toExternalForm())};
    private MainPlayer owner;
    private double timeCount;
    private double status;
    private double bombCount;
    public Bomb(int i, int j, Board board) {
        super(i,j,board);
        isAllowMove = true;
        status = 0;
        image = images[0];
        timeCount=0;
    }
    public Bomb(int i, int j, Board board, MainPlayer owner) {
        super(i,j,board);
        this.owner = owner;
        isAllowMove = true;
        status = 0;
        image = images[0];
        timeCount=0;
        bombCount = 2;
    }

    @Override
    public void render(GraphicsContext gameDraw) {
        gameDraw.drawImage(image,this.locationX*GameSetting.blocks.get(),this.locationY*GameSetting.blocks.get(), GameSetting.blocks.get(),GameSetting.blocks.get());
    }

    public boolean getAllowMove(Object obj) {
        if (obj == owner) return isAllowMove;
        else return false;
    }

    @Override
    public void update(double dt) {
        status = timeCount/bombCount*images.length;
        image = images[(int)status%images.length];
        timeCount += dt;
        if (isAllowMove) {
            double xDistance = Math.abs(owner.getX() - this.locationX);
            double yDistance = Math.abs(owner.getY() - this.locationY);
            if (xDistance > 1 || yDistance > 1) this.isAllowMove=false;
        }

        if (timeCount > bombCount) {
            owner.bombBlowed();
            board.getRemoveList().add(new RunLater(this.locationX,this.locationY,this));
            this.executeFrame();
        }
    }

    private void executeFrame() {
        SoundTools.BOMBBANG.play();
        board.getMap()[locationX][locationY].getAddLater().add(new RunLater(0,0,new Frame(locationX,locationY,board,Frame.FRAMETYPE_MID)));
        for (int i=0;i<owner.getBombSize();i++) {
            int j = i + 1;
            if (locationX - j >= 0) {
                if (board.getMap()[locationX - j][locationY].isEmpty())
                    board.getMap()[locationX - j][locationY].getAddLater().add(new RunLater(0, 0, new Frame(locationX - j, locationY, board, Frame.FRAMETYPE_LEFT)));
                else if (board.getMap()[locationX - j][locationY].getEntity().getAllowMove(this)) {
                    board.getMap()[locationX - j][locationY].getAddLater().add(new RunLater(0, 0, new Frame(locationX - j, locationY, board, Frame.FRAMETYPE_LEFT)));
                } else if (board.getMap()[locationX - j][locationY].getEntity().getDetroyalbe()) {
                    board.getMap()[locationX - j][locationY].getAddLater().add(new RunLater(0, 0, new Frame(locationX - j, locationY, board, Frame.FRAMETYPE_LEFT)));
                    break;
                } else break;
            }
        }
        for (int i=0;i<owner.getBombSize();i++) {
            int j=i+1;
            if (locationX + j < GameSetting.GAMESCALEMAP) {
                if (board.getMap()[locationX+j][locationY].isEmpty()) board.getMap()[locationX+j][locationY].getAddLater().add(new RunLater(0,0,new Frame(locationX+j,locationY,board,Frame.FRAMETYPE_RIGHT)));
                else if (board.getMap()[locationX+j][locationY].getEntity().getAllowMove(this)) {
                    board.getMap()[locationX+j][locationY].getAddLater().add(new RunLater(0,0,new Frame(locationX+j,locationY,board,Frame.FRAMETYPE_RIGHT)));
                } else if (board.getMap()[locationX+j][locationY].getEntity().getDetroyalbe()) {
                    board.getMap()[locationX+j][locationY].getAddLater().add(new RunLater(0,0,new Frame(locationX+j,locationY,board,Frame.FRAMETYPE_RIGHT)));
                    break;
                } else break;
            }
        }

        for (int i=0;i<owner.getBombSize();i++) {
            int j=i+1;
            if (locationY -j >= 0) {
                if (board.getMap()[locationX][locationY-j].isEmpty()) board.getMap()[locationX][locationY-j].getAddLater().add(new RunLater(0,0,new Frame(locationX,locationY-j,board,Frame.FRAMETYPE_UP)));
                else if (board.getMap()[locationX][locationY-j].getEntity().getAllowMove(this)) {
                    board.getMap()[locationX][locationY-j].getAddLater().add(new RunLater(0,0,new Frame(locationX,locationY-j,board,Frame.FRAMETYPE_UP)));
                } else if (board.getMap()[locationX][locationY-j].getEntity().getDetroyalbe()) {
                    board.getMap()[locationX][locationY-j].getAddLater().add(new RunLater(0,0,new Frame(locationX,locationY-j,board,Frame.FRAMETYPE_UP)));
                    break;
                } else break;
            }
        }

        for (int i=0;i<owner.getBombSize();i++) {
            int j=i+1;
            if (locationY +j < GameSetting.GAMESCALEMAP-1) {
                if (board.getMap()[locationX][locationY+j].isEmpty()) board.getMap()[locationX][locationY+j].getAddLater().add(new RunLater(0,0,new Frame(locationX,locationY+j,board,Frame.FRAMETYPE_DOWN)));
                else if (board.getMap()[locationX][locationY+j].getEntity().getAllowMove(this)) {
                    board.getMap()[locationX][locationY+j].getAddLater().add(new RunLater(0,0,new Frame(locationX,locationY+j,board,Frame.FRAMETYPE_DOWN)));
                } else if (board.getMap()[locationX][locationY+j].getEntity().getDetroyalbe()) {
                    board.getMap()[locationX][locationY+j].getAddLater().add(new RunLater(0,0,new Frame(locationX,locationY+j,board,Frame.FRAMETYPE_DOWN)));
                    break;
                } else break;
            }
        }
    }

}

