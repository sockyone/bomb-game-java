package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class Game extends AnimationTimer {

    private long oldTime;;
    private GraphicsContext gameDraw;
    private Board board;
    private boolean start;
    private GameStatus gameStatus;

    public Game(GraphicsContext gameDraw, Canvas keyHandler,GameStatus gameStatus) {
        start = false;
        board = new Board(gameDraw,keyHandler,gameStatus);
        this.gameStatus = gameStatus;
        this.gameDraw = gameDraw;
        try {
            board.loadMap(1);
        } catch (Exception e) {
            System.out.println("Can't load map.");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }


    @Override
    public void handle(long now) {
        if (gameStatus.isDead) {
            gameDraw.clearRect(0,0,GameSetting.screenGameSize.get(),GameSetting.screenGameSize.get());
            this.stop();
        }
        if (start == false) {
            start = true;
            oldTime=now;
        }
        board.update((now-oldTime)/1000000000.0f);
        oldTime = now;
        board.render();
    }

}
