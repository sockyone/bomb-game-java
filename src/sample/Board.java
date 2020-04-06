package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import sample.Entity.*;
import sample.Entity.Enemy.Enemy;
import sample.Entity.Enemy.NormalEnemy;
import sample.Entity.Enemy.TankerEnemy;
import sample.Entity.MapEntity.*;
import sample.Entity.PlayerEntity.MainPlayer;
import sample.Tools.RunLater;

public class Board {
    private boolean isFinishThisLevel;
    private GraphicsContext gameDraw;
    private Image background;
    private EntityLayer[][] map = new EntityLayer[GameSetting.GAMESCALEMAP][GameSetting.GAMESCALEMAP];
    private MainPlayer mainPlayer;
    private Canvas keyHandler;
    private ArrayList<RunLater> removeList;
    private ArrayList<RunLater> removeEnemyList;
    private int level;
    public GameStatus gameStatus;

    private ArrayList<Enemy> enemyList;

    public Board(GraphicsContext gameDraw, Canvas keyHandler,GameStatus gameStatus) {
        this.keyHandler = keyHandler;
        this.gameDraw = gameDraw;
        this.gameStatus = gameStatus;
        removeList = new ArrayList<>();
        enemyList = new ArrayList<>();
        removeEnemyList=new ArrayList<>();
        background = new Image(getClass().getResource("image/background.jpg").toExternalForm());
        for (int i=0;i<GameSetting.GAMESCALEMAP;i++) {
            for (int j = 0; j < GameSetting.GAMESCALEMAP; j++) {
                map[i][j] = new EntityLayer(gameDraw);
            }
        }
    }

    public ArrayList<RunLater> getRemoveEnemyList() {
        return this.removeEnemyList;
    }

    public ArrayList<Enemy> getEnemyList() {
        return enemyList;
    }

    public MainPlayer getMainPlayer() {
        return mainPlayer;
    }

    public void destroyMainPlayer() {
        SoundTools.MOVESOUND.stop();
        mainPlayer.destroy();
        mainPlayer=null;
        gameStatus.isDead = true;
        System.gc();
    }

    public ArrayList<RunLater> getRemoveList() {
        return removeList;
    }

    public void load() {
        for (int i=0;i<GameSetting.GAMESCALEMAP;i++) {
            for (int j=0;j<GameSetting.GAMESCALEMAP;j++) {
                map[i][j] = new EntityLayer(gameDraw);
            }
        }
        enemyList.clear();
        mainPlayer=null;
        try {
            this.loadMap(++level);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        gameStatus.setLevel(level);
    }

    public void loadMap(int level) throws Exception {
        this.level=level;
        this.isFinishThisLevel=false;
        String url = "map/map" + level + ".txt";
        System.out.println(url);
        Scanner scanner = new Scanner(new File(getClass().getResource(url).toURI()));
        while (scanner.hasNextInt()) {
            for (int j=0;j<GameSetting.GAMESCALEMAP;j++) {
                for (int i=0;i<GameSetting.GAMESCALEMAP;i++) {
                    switch (scanner.nextInt()) {
                        case GameSetting.STONE: map[i][j].addEntity(new Stone(i,j,this)); break;
                        case GameSetting.WOOD: map[i][j].addEntity(new Wood(i,j,this)); break;
                        case GameSetting.TREE: map[i][j].addEntity(new Tree(i,j,this)); break;
                        case GameSetting.ITEMBOMB: map[i][j].addEntity(new ItemBomb(i,j,this)); break;
                        case GameSetting.ITEMBOMBSIZE: map[i][j].addEntity(new ItemBombSize(i,j,this)); break;
                        case GameSetting.ITEMSHOE: map[i][j].addEntity(new ItemShoe(i,j,this)); break;
                        case GameSetting.PORTALEND:  map[i][j].addEntity(new Portal(i,j,this));break;
                        case GameSetting.MAINPLAYER : this.mainPlayer = new MainPlayer(i,j,this); mainPlayer.setHandler(this.keyHandler); break;
                        case GameSetting.ENEMYNORMAL : this.enemyList.add(new NormalEnemy(i,j,this)); break;
                        case GameSetting.ENEMYTANKER : this.enemyList.add(new TankerEnemy(i,j,this)); break;
                    }
                }
            }
        }
        for (Enemy i:enemyList) {
            i.firstLoadDirection();
        }
    }

    public EntityLayer[][] getMap() {
        return this.map;
    }


    public void update(double dt) {
        if (mainPlayer!=null)
        mainPlayer.update(dt);
        for (int i=0;i<GameSetting.GAMESCALEMAP;i++) {
            for (int j = 0; j < GameSetting.GAMESCALEMAP; j++) {
                map[i][j].update(dt);
            }
        }
        for (Enemy i:enemyList) {
            i.update(dt);
        }
        for (RunLater i:this.removeList) {
            map[i.x][i.y].getEntities().remove(i.obj);
        }
        removeList.clear();
        for (RunLater i:this.removeEnemyList) {
            enemyList.remove(i.obj);
        }

        if (enemyList.isEmpty()) isFinishThisLevel = true;
    }

    public boolean isFinishThisLevel() {
        return this.isFinishThisLevel;
    }

    public void render() {
        gameDraw.drawImage(background,0,0,GameSetting.screenGameSize.get(),GameSetting.screenGameSize.get());
        for (int i=0;i<GameSetting.GAMESCALEMAP;i++) {
            for (int j = 0; j < GameSetting.GAMESCALEMAP; j++) {
                map[i][j].render();
            }
        }
        for (Enemy i:enemyList) {
            i.render(gameDraw);
        }
        if (mainPlayer != null)
        mainPlayer.render(gameDraw);
    }

}
