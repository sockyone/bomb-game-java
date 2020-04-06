package sample.Entity.MapEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Board;
import sample.Entity.Entity;
import sample.GameSetting;

public class Stone extends Entity {
    private static Image imageDefault = new Image(Stone.class.getResource("../../image/stone.png").toExternalForm());
    public Stone(int i, int j, Board board) {
        super(i,j,board);
        isAllowMove = false;
        image = imageDefault;
    }


    @Override
    public void render(GraphicsContext gameDraw) {
        gameDraw.drawImage(image,this.locationX*GameSetting.blocks.get()
                ,this.locationY* GameSetting.blocks.get(),GameSetting.blocks.get(),GameSetting.blocks.get());
    }

    @Override
    public void update(double dt) {

    }
}
