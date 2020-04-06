package sample.Entity.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Board;
import sample.GameSetting;
import sample.Tools.BreadthFirstPaths;
import sample.Tools.Graph;

import java.util.Stack;

public class TankerEnemy extends Enemy {

    private static Image imageLeft = new Image(NormalEnemy.class.getResource("../../image/bossTank_left.png").toExternalForm());
    private static Image imageRight = new Image(NormalEnemy.class.getResource("../../image/bossTank_right.png").toExternalForm());
    private static Image imageDown = new Image(NormalEnemy.class.getResource("../../image/bossTank_down.png").toExternalForm());
    private static Image imageUp = new Image(NormalEnemy.class.getResource("../../image/bossTank_up.png").toExternalForm());

    private Image image;
    public TankerEnemy(int i, int j, Board board) {
        super(i, j, board);
        speed = Enemy.ENEMYTANKER_SPEED;
        this.point = 80;
        this.isAllowMove = true;
    }


    @Override
    public void firstLoadDirection() {
        handleNextMove();
    }

    public boolean getAllowMove(Object obj) {
        if (obj instanceof Enemy) return false;
        return isAllowMove;
    }

    @Override
    protected void handleNextMove() {
        Graph graph = new Graph(GameSetting.GAMESCALEMAP*GameSetting.GAMESCALEMAP);
        for (int i=0;i<GameSetting.GAMESCALEMAP;i++) {
            for (int j=0;j<GameSetting.GAMESCALEMAP;j++) {
                if (!board.getMap()[i][j].isEmpty())
                    if (!board.getMap()[i][j].getEntity().getAllowMove(this)) continue;
                //check top:
                if (j-1 >= 0) {
                    if (board.getMap()[i][j-1].isEmpty()) {
                        graph.addEdge(i*GameSetting.GAMESCALEMAP+j,i*GameSetting.GAMESCALEMAP+j-1);
                    } else if (board.getMap()[i][j-1].getEntity().getAllowMove(this)) {
                        graph.addEdge(i*GameSetting.GAMESCALEMAP+j,i*GameSetting.GAMESCALEMAP+j-1);
                    }
                }
                //check bot
                if (j+1 < GameSetting.GAMESCALEMAP) {
                    if (board.getMap()[i][j+1].isEmpty()) {
                        graph.addEdge(i*GameSetting.GAMESCALEMAP+j,i*GameSetting.GAMESCALEMAP+j+1);
                    } else if (board.getMap()[i][j+1].getEntity().getAllowMove(this)) {
                        graph.addEdge(i*GameSetting.GAMESCALEMAP+j,i*GameSetting.GAMESCALEMAP+j+1);
                    }
                }
                //check left
                if (i-1 >= 0) {
                    if (board.getMap()[i-1][j].isEmpty()) {
                        graph.addEdge(i*GameSetting.GAMESCALEMAP+j,(i-1)*GameSetting.GAMESCALEMAP+j);
                    } else if (board.getMap()[i-1][j].getEntity().getAllowMove(this)) {
                        graph.addEdge(i*GameSetting.GAMESCALEMAP+j,(i-1)*GameSetting.GAMESCALEMAP+j);
                    }
                }
                //check right
                if (i+1 < GameSetting.GAMESCALEMAP) {
                    if (board.getMap()[i+1][j].isEmpty()) {
                        graph.addEdge(i*GameSetting.GAMESCALEMAP+j,(i+1)*GameSetting.GAMESCALEMAP+j);
                    } else if (board.getMap()[i+1][j].getEntity().getAllowMove(this)) {
                        graph.addEdge(i*GameSetting.GAMESCALEMAP+j,(i+1)*GameSetting.GAMESCALEMAP+j);
                    }
                }
            }
        }
        BreadthFirstPaths bfs = new BreadthFirstPaths(graph, locationX*GameSetting.GAMESCALEMAP+locationY);
        if (board.getMainPlayer() != null) {
            if (locationX*GameSetting.GAMESCALEMAP+locationY == (int)(board.getMainPlayer().getX()+0.5)*GameSetting.GAMESCALEMAP+(int)(board.getMainPlayer().getY()+0.5)) {
                image = imageDown;
                direction = GameSetting.DIRECTION_NONE;
            }
            if (bfs.hasPathTo((int)(board.getMainPlayer().getX()+0.5)*GameSetting.GAMESCALEMAP+(int)(board.getMainPlayer().getY()+0.5))) {
                Iterable<Integer> path = bfs.pathTo((int)(board.getMainPlayer().getX()+0.5)*GameSetting.GAMESCALEMAP+(int)(board.getMainPlayer().getY()+0.5));
                for (Integer i:path) {
                    int locationX = i/GameSetting.GAMESCALEMAP;
                    int locationY = i%GameSetting.GAMESCALEMAP;
                    if (locationX > this.locationX) {
                        direction = GameSetting.DIRECTION_RIGHT;
                        image = imageRight;
                    } else if (locationX < this.locationX) {
                        direction = GameSetting.DIRECTION_LEFT;
                        image = imageLeft;
                    } else if (locationY < this.locationY) {
                        direction = GameSetting.DIRECTION_UP;
                        image = imageUp;
                    } else if (locationY > this.locationY) {
                        direction = GameSetting.DIRECTION_DOWN;
                        image = imageDown;
                    }
                }
            } else {
                image = imageDown;
                direction = GameSetting.DIRECTION_NONE;
                //System.out.println("this one happened");
            }
        } else {
            image = imageDown;
            direction = GameSetting.DIRECTION_NONE;
        }
    }

    @Override
    public void render(GraphicsContext gameDraw) {
        gameDraw.drawImage(image,x*GameSetting.blocks.get(),y*GameSetting.blocks.get(),GameSetting.blocks.get(),GameSetting.blocks.get());

    }

    @Override
    protected void move(double dt) {
        super.move(dt);


    }

    @Override
    public void update(double dt) {
        move(dt);
        if (Math.abs(lengthStreetPass-1)<=0.05f || lengthStreetPass==0) {
            locationY = (int)(y+0.5);
            locationX = (int)(x+0.5);
            x=locationX;
            y=locationY;
            lengthStreetPass=0;
            if (board.getMainPlayer()!=null) {
                if (locationX != (int)(board.getMainPlayer().getX()+0.5) || locationY != (int)(board.getMainPlayer().getY()+0.5)) handleNextMove();
            } else direction = GameSetting.DIRECTION_NONE;
        }
        if (board.getMainPlayer() != null) {
            if (this.isCollide(board.getMainPlayer().getX(),board.getMainPlayer().getY(),0.15f)) {
                board.destroyMainPlayer();
            }
        }
    }
}
