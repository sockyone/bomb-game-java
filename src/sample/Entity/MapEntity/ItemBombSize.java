package sample.Entity.MapEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Board;
import sample.Entity.Entity;
import sample.GameSetting;
import sample.Tools.RunLater;

public class ItemBombSize extends Entity {
    private static Image imageDefault = new Image(ItemBomb.class.getResource("../../image/item_bombsize.png").toExternalForm());


    public ItemBombSize(int i, int j, Board board) {
        super(i,j,board);
        detroyalbe = false;
        isAllowMove = true;
        image = imageDefault;
    }

    @Override
    public void render(GraphicsContext gameDraw) {
        gameDraw.drawImage(image,locationX * GameSetting.blocks.get(),locationY*GameSetting.blocks.get(),GameSetting.blocks.get(),GameSetting.blocks.get());
    }

    @Override
    public void update(double dt) {
//        double x = board.getMainPlayer().getX();
//        double y = board.getMainPlayer().getY();
//        double xDistance = Math.abs(x-this.locationX);
//        double yDistance = Math.abs(y-this.locationY);
//        if (xDistance < 0.5f && yDistance < 0.5f) {
//            board.getMainPlayer().setBombSizeUp();
//            board.getRemoveList().add(new RunLater(locationX,locationY,this));
//        }
    }

    public void remove() {
        board.getRemoveList().add(new RunLater(locationX,locationY,this));
    }
}