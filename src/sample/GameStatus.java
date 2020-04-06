package sample;

import javafx.scene.control.Label;

public class GameStatus {
    public int score;
    public boolean isPassed;
    public int level;
    public Label levelLabel;
    public Label scoreLabel;
    public boolean isDead;

    public GameStatus(Label score,Label level) {
        // first init
        this.score = 0;
        isPassed = false;
        this.level = 1;
        levelLabel = level;
        scoreLabel = score;
        isDead = false;
        this.levelLabel.setText(new Integer(this.level).toString());
        System.out.println(new Integer(this.level).toString());
        this.scoreLabel.setText(new Integer(this.score).toString());
    }

    public void setLevel(int level) {
        this.level = level;
        this.levelLabel.setText(new Integer(this.level).toString());
    }

    public void upScore(int scoreUp) {
        this.score += scoreUp;
        this.scoreLabel.setText(new Integer(this.score).toString());
    }

}
