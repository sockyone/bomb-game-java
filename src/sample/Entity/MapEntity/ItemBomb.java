package sample.Entity.MapEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Board;
import sample.Entity.Entity;
import sample.GameSetting;

public class ItemBomb extends Entity {

    private static Image imageDefault = new Image(ItemBomb.class.getResource("../../image/item_bomb.png").toExternalForm());

    public ItemBomb(int i, int j, Board board) {
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

    }
}