package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class GameSetting {
    public static DoubleProperty blocks = new SimpleDoubleProperty();
    public static DoubleProperty screenGameSize = new SimpleDoubleProperty();
    public final static int GAMESCALEMAP = 15;

    public final static int STONE = 1;
    public final static int WOOD = 2;
    public final static int TREE = 3;
    public final static int ITEMBOMB = 4;
    public final static int ITEMBOMBSIZE = 5;
    public final static int ITEMSHOE = 6;
    public final static int PLAYERSTART = 7;
    public final static int PORTALEND = 8;
    public final static int MAINPLAYER = 9;
    public final static int ENEMYNORMAL = 10;
    public final static int ENEMYTANKER = 11;
    public final static int ENEMYBOSS = 12;



    public final static double ITEMTIME = 5;



    public final static int DIRECTION_UP = 0;
    public final static int DIRECTION_DOWN = 1;
    public final static int DIRECTION_RIGHT = 2;
    public final static int DIRECTION_LEFT = 3;
    public final static int DIRECTION_NONE = 4;



    public static double PLAYER_SPEED_DEFAULT = 4.0f;
}
