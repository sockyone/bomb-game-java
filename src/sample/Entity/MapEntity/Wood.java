package sample.Entity.MapEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Board;
import sample.Entity.Entity;
import sample.GameSetting;

public class Wood extends Entity {
    private static Image imageDefault = new Image(Wood.class.getResource("../../image/wood.png").toExternalForm());

    public Wood(int i, int j, Board board) {
        super(i,j,board);
        isAllowMove = false;
        this.detroyalbe = true;
        image=imageDefault;
        this.point = 10;
    }

    @Override
    public void render(GraphicsContext gameDraw) {
        gameDraw.drawImage(image,locationX* GameSetting.blocks.get(),locationY*GameSetting.blocks.get(),GameSetting.blocks.get(),GameSetting.blocks.get());
    }

    @Override
    public void update(double dt) {

    }
}
