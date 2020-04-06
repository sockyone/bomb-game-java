package sample.Entity.PlayerEntity;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Board;
import sample.Entity.Entity;
import sample.Entity.MapEntity.*;
import sample.Game;
import sample.GameSetting;
import sample.SoundTools;
import sample.Tools.RunLater;


public class MainPlayer extends Entity  {
    private double dx;
    private double dy;
    private double dt;
    private Canvas keyHandler;
    private double status;
    private boolean isMoving;
    //public boolean isBombExist;
    private double speed;
    private int bombSize;
    private double bombSizeCountTime;
    private double bombItemCountTime;
    private double speedItemCountTime;
    private int maxBombCount;
    private static Image[] imagesDown = {new Image(MainPlayer.class.getResource("../../image/player_down_1.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_down_2.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_down_3.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_down_4.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_down_5.png").toExternalForm())};
    private static Image[] imagesUp = {new Image(MainPlayer.class.getResource("../../image/player_up_1.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_up_2.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_up_3.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_up_4.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_up_5.png").toExternalForm())};
    private static Image[] imagesLeft = {new Image(MainPlayer.class.getResource("../../image/player_left_1.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_left_2.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_left_3.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_left_4.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_left_5.png").toExternalForm())};
    private static Image[] imagesRight = {new Image(MainPlayer.class.getResource("../../image/player_right_1.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_right_2.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_right_3.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_right_4.png").toExternalForm())
            ,new Image(MainPlayer.class.getResource("../../image/player_right_5.png").toExternalForm())};
    private int direction;
    private int availableBomb;

    private EventHandler<KeyEvent> keyPressedDetected;
    private EventHandler<KeyEvent> keyRealeasedDetected;
    public MainPlayer(int i, int j,Board board) {
        //setup
        super(i,j,board);
        status = 0;
        speed = GameSetting.PLAYER_SPEED_DEFAULT;
        this.x = locationX;
        this.y = locationY;
        this.image = MainPlayer.imagesDown[0];
        this.isMoving = false;
        //isBombExist = false;
        direction = GameSetting.DIRECTION_DOWN;
        isAllowMove = false;
        bombSize = 1;
        availableBomb = 1;
        bombItemCountTime = 0;
        bombSizeCountTime = 0;
        maxBombCount = 1;
        speedItemCountTime=0;
        detroyalbe = true;
    }

    public void bombBlowed() {
        availableBomb++;
    }

    public int getBombSize() {
        return bombSize;
    }

    public void setBombSizeUp() {
        bombSize++;
    }


    public void setHandler(Canvas keyHandler) {
        this.keyHandler = keyHandler;
        this.keyPressedDetected = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                    if (!isMoving) {
                        dy = -1;
                        direction = GameSetting.DIRECTION_UP;
                        isMoving = true;
                    }
                }
                else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                    if (!isMoving) {
                        dy = 1 ;
                        direction = GameSetting.DIRECTION_DOWN;
                        isMoving = true;
                    }

                }
                else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                    if (!isMoving) {
                        dx = -1;
                        direction = GameSetting.DIRECTION_LEFT;
                        isMoving = true;
                    }
                }
                else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                    if (!isMoving) {
                        dx = 1;
                        direction = GameSetting.DIRECTION_RIGHT;
                        isMoving = true;
                    }
                }
                if (event.getCode() == KeyCode.SPACE) {
                    setUpBomb();
                }
            }
        };
        keyHandler.setOnKeyPressed(keyPressedDetected);

        this.keyRealeasedDetected = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if ((event.getCode() == KeyCode.W && direction == GameSetting.DIRECTION_UP)
                    || (event.getCode() == KeyCode.S && direction == GameSetting.DIRECTION_DOWN)
                    || (event.getCode() == KeyCode.A && direction == GameSetting.DIRECTION_LEFT)
                    || (event.getCode() == KeyCode.D && direction == GameSetting.DIRECTION_RIGHT)
                    || (event.getCode() == KeyCode.UP && direction == GameSetting.DIRECTION_UP)
                    || (event.getCode() == KeyCode.DOWN && direction == GameSetting.DIRECTION_DOWN)
                    || (event.getCode() == KeyCode.LEFT && direction == GameSetting.DIRECTION_LEFT)
                    || (event.getCode() == KeyCode.RIGHT && direction == GameSetting.DIRECTION_RIGHT))
                {
                    dx=0;
                    dy=0;
                    isMoving = false;
                }


            }
        };

        keyHandler.setOnKeyReleased(keyRealeasedDetected);

    }


    @Override
    public void render(GraphicsContext gameDraw) {
        gameDraw.drawImage(image,x*GameSetting.blocks.get(),y*GameSetting.blocks.get(),GameSetting.blocks.get(),GameSetting.blocks.get());
    }

    @Override
    public void update(double dt) {
        double dx = this.dx*dt;
        double dy = this.dy*dt;
        //keyHandler.setOnKeyPressed(null);
        //keyHandler.setOnKeyReleased(null);
        //System.out.println(checkCollide(dx,dy));
        if (!checkCollide(dx,dy)) {
            x+=dx*speed;
            y+=dy*speed;
        } else {
            //check left
            if (Math.abs(x-(int)(x+0.5f)) < 0.15f) x = (int)(x+0.5f);
            if (Math.abs(y-(int)(y+0.5f)) < 0.15f) y = (int)(y+0.5f);
            if (direction == GameSetting.DIRECTION_LEFT) {
                //take it up
                if (x - 1 < 0 || y + 1 > GameSetting.GAMESCALEMAP - 1) {
                } else if (!board.getMap()[(int) (this.x - 1)][(int) (this.y + 1)].isEmpty()) {
                    if (!board.getMap()[(int) (this.x - 1)][(int) (this.y + 1)].getEntity().getAllowMove(this)) {
                        if (board.getMap()[(int) (x - 1)][(int) y].isEmpty()) {
                            if (Math.abs(this.y - (int) y) < 0.5f) {
                                this.y = (int) y;
                                this.x += dx;
                            }
                        } else if (board.getMap()[(int) (x - 1)][(int) y].getEntity().getAllowMove(this)) {
                            if (Math.abs(this.y - (int) y) < 0.5f) {
                                this.y = (int) y;
                                this.x += dx;
                            }
                        }
                    }
                }
                //take it down
                if (x - 1 < 0) {
                } else if (!board.getMap()[(int) (this.x - 1)][(int) (this.y)].isEmpty()) {
                    if (!board.getMap()[(int) (this.x - 1)][(int) (this.y)].getEntity().getAllowMove(this)) {
                        if (board.getMap()[(int) (x - 1)][(int) (y + 1)].isEmpty()) {
                            if (Math.abs(this.y - (int) (y + 1)) < 0.5f) {
                                this.y = (int) (y + 1);
                                this.x += dx;
                            }
                        } else if (board.getMap()[(int) (x - 1)][(int) (y + 1)].getEntity().getAllowMove(this)) {
                            if (Math.abs(this.y - (int) (y + 1)) < 0.5f) {
                                this.y = (int) (y + 1);
                                this.x += dx;
                            }
                        }
                    }
                }
            } else if (direction == GameSetting.DIRECTION_RIGHT) {     //check right
                //take it up
                if (x + 1 > GameSetting.GAMESCALEMAP - 1 || y + 1 > GameSetting.GAMESCALEMAP - 1) {
                } else if (!board.getMap()[(int) (this.x + 1)][(int) (this.y + 1)].isEmpty()) {
                    if (!board.getMap()[(int) (this.x + 1)][(int) (this.y + 1)].getEntity().getAllowMove(this)) {
                        if (board.getMap()[(int) (x + 1)][(int) (y)].isEmpty()) {
                            if (Math.abs(this.y - (int) y) < 0.5f) {
                                this.y = (int) y;
                                this.x += dx;
                            }
                        } else if (board.getMap()[(int) (x + 1)][(int) (y)].getEntity().getAllowMove(this)) {
                            if (Math.abs(this.y - (int) y) < 0.5f) {
                                this.y = (int) y;
                                this.x += dx;
                            }
                        }
                    }
                }
                //take it down
                if (x + 1 > GameSetting.GAMESCALEMAP - 1) {
                } else if (!board.getMap()[(int) (this.x + 1)][(int) (this.y)].isEmpty()) {
                    if (!board.getMap()[(int) (this.x + 1)][(int) (this.y)].getEntity().getAllowMove(this)) {
                        if (board.getMap()[(int) (x + 1)][(int) (y + 1)].isEmpty()) {
                            if (Math.abs(this.y - (int) (y + 1)) < 0.5f) {
                                this.y = (int) (y + 1);
                                this.x += dx;
                            }
                        } else if (board.getMap()[(int) (x + 1)][(int) (y + 1)].getEntity().getAllowMove(this)) {
                            if (Math.abs(this.y - (int) (y + 1)) < 0.5f) {
                                this.y = (int) (y + 1);
                                this.x += dx;
                            }
                        }
                    }
                }
            } else if (direction == GameSetting.DIRECTION_DOWN) {   //check bot
                //take it left
                if (x + 1 > GameSetting.GAMESCALEMAP - 1 || y + 1 > GameSetting.GAMESCALEMAP - 1) {
                } else if (!board.getMap()[(int) (this.x + 1)][(int) (this.y + 1)].isEmpty()) {
                    if (!board.getMap()[(int) (this.x + 1)][(int) (this.y + 1)].getEntity().getAllowMove(this)) {
                        if (board.getMap()[(int) (x)][(int) (y + 1)].isEmpty()) {
                            if (Math.abs(this.x - (int) x) < 0.5f) {
                                this.x = (int) x;
                                this.y += dy;
                            }
                        } else if (board.getMap()[(int) (x)][(int) (y + 1)].getEntity().getAllowMove(this)) {
                            if (Math.abs(this.x - (int) x) < 0.5f) {
                                this.x = (int) x;
                                this.y += dy;
                            }
                        }
                    }
                }
                //take it right
                if (y + 1 > GameSetting.GAMESCALEMAP - 1) {
                } else if (!board.getMap()[(int) (this.x)][(int) (this.y + 1)].isEmpty()) {
                    if (!board.getMap()[(int) (this.x)][(int) (this.y + 1)].getEntity().getAllowMove(this)) {
                        if (board.getMap()[(int) (x + 1)][(int) (y + 1)].isEmpty()) {
                            if (Math.abs(this.x - (int) (x + 1)) < 0.5f) {
                                this.x = (int) (x + 1);
                                this.y += dy;
                            }
                        } else if (board.getMap()[(int) (x + 1)][(int) (y + 1)].getEntity().getAllowMove(this)) {
                            if (Math.abs(this.x - (int) (x + 1)) < 0.5f) {
                                this.x = (int) (x + 1);
                                this.y += dy;
                            }
                        }
                    }
                }
            } else if (direction == GameSetting.DIRECTION_UP) { //check top
                //take it left
                if (x + 1 > GameSetting.GAMESCALEMAP - 1 || y - 1 < 0) {
                } else if (!board.getMap()[(int) (this.x + 1)][(int) (this.y - 1)].isEmpty()) {
                    if (!board.getMap()[(int) (this.x + 1)][(int) (this.y - 1)].getEntity().getAllowMove(this)) {
                        if (board.getMap()[(int) (x)][(int) (y - 1)].isEmpty()) {
                            if (Math.abs(this.x - (int) x) < 0.5f) {
                                this.x = (int) x;
                                this.y += dy;
                            }
                        } else if (board.getMap()[(int) (x)][(int) (y - 1)].getEntity().getAllowMove(this)) {
                            if (Math.abs(this.x - (int) x) < 0.5f) {
                                this.x = (int) x;
                                this.y += dy;
                            }
                        }
                    }
                }
                //take it right
                if (y - 1 < 0) {
                } else if (!board.getMap()[(int) (this.x)][(int) (this.y - 1)].isEmpty()) {
                    if (!board.getMap()[(int) (this.x)][(int) (this.y - 1)].getEntity().getAllowMove(this)) {
                        if (board.getMap()[(int) (x + 1)][(int) (y - 1)].isEmpty()) {
                            if (Math.abs(this.x - (int) (x + 1)) < 0.5f) {
                                this.x = (int) (x + 1);
                                this.y += dy;
                            }
                        } else if (board.getMap()[(int) (x + 1)][(int) (y - 1)].getEntity().getAllowMove(this)) {
                            if (Math.abs(this.x - (int) (x + 1)) < 0.5f) {
                                this.x = (int) (x + 1);
                                this.y += dy;
                            }
                        }
                    }
                }
            }

        }


        //check get item
        if (!this.board.getMap()[(int)(x+0.5)][(int)(y+0.5)].isEmpty()) {
            Entity entity = this.board.getMap()[(int)(x+0.5)][(int)(y+0.5)].getEntity();
            if (entity instanceof ItemBombSize) {
                SoundTools.ITEMSOUND.play();
                board.getRemoveList().add(new RunLater((int) (x + 0.5), (int) (y + 0.5), entity));
                setBombSizeUp();
            } else if (entity instanceof ItemBomb) {
                SoundTools.ITEMSOUND.play();
                board.getRemoveList().add(new RunLater((int) (x + 0.5), (int) (y + 0.5), entity));
                setAvailableBombUp();
            } else if (entity instanceof ItemShoe) {
                SoundTools.ITEMSOUND.play();
                board.getRemoveList().add(new RunLater((int) (x + 0.5), (int) (y + 0.5), entity));
                setSpeedUp();
            } else if (entity instanceof Portal) {
                ((Portal)entity).nextLevel();
            }
        }
        if (bombSize > 1) {
            bombSizeCountTime += dt;
            if (bombSizeCountTime > GameSetting.ITEMTIME) {
                bombSizeCountTime = 0;
                bombSize --;
            }
        }

        if (maxBombCount > 1) {
            bombItemCountTime += dt;
            if (bombItemCountTime > GameSetting.ITEMTIME) {
                bombItemCountTime = 0;
                availableBomb--;
                maxBombCount--;
            }
        }

        if (speed > GameSetting.PLAYER_SPEED_DEFAULT) {
            speedItemCountTime += dt;
            if (speedItemCountTime > GameSetting.ITEMTIME) {
                speed = GameSetting.PLAYER_SPEED_DEFAULT;
                speedItemCountTime = 0;
            }
        }


//        System.out.println("x= "+x+",y= "+y);
        if (isMoving) {
            status += 8*dt;
            if (!SoundTools.MOVESOUND.isRunning()) SoundTools.MOVESOUND.play();
        } else {
            if (SoundTools.MOVESOUND.isRunning()) SoundTools.MOVESOUND.stop();
        }
        switch (direction) {
            case GameSetting.DIRECTION_UP: image = imagesUp[(int)(status%imagesUp.length)]; break;
            case GameSetting.DIRECTION_DOWN: image = imagesDown[(int)(status%imagesDown.length)]; break;
            case GameSetting.DIRECTION_LEFT: image = imagesLeft[(int)(status%imagesLeft.length)]; break;
            case GameSetting.DIRECTION_RIGHT: image = imagesRight[(int)(status%imagesRight.length)]; break;
            default: break;
        }
        if (status > 5000000.0f) status = 0;
        //keyHandler.setOnKeyPressed(keyPressedDetected);
        //keyHandler.setOnKeyReleased(keyRealeasedDetected);
    }

    private boolean checkCollide(double dx,double dy) {
        if (this.x+dx <0 || this.x+dx > GameSetting.GAMESCALEMAP-1) return true;

        if (this.y+dy <0 || this.y+dy > GameSetting.GAMESCALEMAP-1) return true;
        double x_ = x+dx;
        double y_ = y+dy;

        //System.out.println("x__=" + x + "y__="+y);
        int xLeft = (int)x_;
        int yTop = (int)y_;
        int xRight = (int)(x_+0.99);
        int yBottom = (int)(y_+0.99);
        if (!board.getMap()[xLeft][yTop].isEmpty()) {
            if (!board.getMap()[xLeft][yTop].getEntity().getAllowMove(this)) {
                return true;
            }
        }
        if (!board.getMap()[xRight][yTop].isEmpty()) {
            if (!board.getMap()[xRight][yTop].getEntity().getAllowMove(this)) {
                return true;
            }
        }
        if (!board.getMap()[xLeft][yBottom].isEmpty()) {
            if (!board.getMap()[xLeft][yBottom].getEntity().getAllowMove(this)) {
                return true;
            }
        }
        if (!board.getMap()[xRight][yBottom].isEmpty()) {
            if (!board.getMap()[xRight][yBottom].getEntity().getAllowMove(this)) {
                return true;
            }
        }

        return false;
    }

    private void setUpBomb() {
        if (availableBomb > 0) {
            availableBomb--;
            SoundTools.BOMBPUTSOUND.play();
            this.board.getMap()[(int)(x+0.5)][(int)(y+0.5)].addEntity(new Bomb((int)(x+0.5),(int)(y+0.5),board,this));
        }
    }

    private void setAvailableBombUp() {
        availableBomb++;
        maxBombCount++;
    }

    private void setSpeedUp() {
        this.speed = 6.0f;
        speedItemCountTime = 0;
    }

    public void destroy() {
        keyHandler.setOnKeyPressed(null);
        keyHandler.setOnKeyReleased(null);
    }
}
