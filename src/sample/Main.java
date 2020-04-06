package sample;

import javafx.application.Application;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.When;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox root = new VBox();
        Canvas gameView = new Canvas();
        gameView.setFocusTraversable(true);

        HBox gameStatusBar = new HBox();
        root.getChildren().add(gameStatusBar);
        gameStatusBar.getChildren().add(new Label("Score: "));
        Label score = new Label("");
        gameStatusBar.getChildren().add(score);
        gameStatusBar.getChildren().add(new Label("||"));
        gameStatusBar.getChildren().add(new Label("Level: "));
        Label level = new Label("");
        gameStatusBar.getChildren().add(level);
        root.getChildren().add(gameView);

        gameStatusBar.setSpacing(10);
        gameStatusBar.setFillHeight(true);
        gameStatusBar.setStyle("-fx-font-size: 30; -fx-alignment: center;");
        GameStatus gameStatus = new GameStatus(score,level);
        NumberBinding min = new When((root.heightProperty()
                .subtract(gameStatusBar.heightProperty()))
                .greaterThanOrEqualTo(root.widthProperty()))
                .then(root.widthProperty())
                .otherwise(root.heightProperty().subtract(gameStatusBar.heightProperty()));


        gameView.widthProperty().bind(min);
        gameView.heightProperty().bind(min);
        GameSetting.screenGameSize.bind(min);
        GameSetting.blocks.bind(min.divide(GameSetting.GAMESCALEMAP));


        GraphicsContext gc = gameView.getGraphicsContext2D();

        Game game = new Game(gc,gameView,gameStatus);

        game.start();



        primaryStage.setTitle("Bomb Game");
        primaryStage.setScene(new Scene(root, 755, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
