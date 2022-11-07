package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Const;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.FileLevelLoad;
import uet.oop.bomberman.entities.bomb.Bomb;

import java.io.File;

public class Bomber extends AnimatedEntity {
//int i = 0;
    private float dx = 0;
    private float dy = 0;
    private float speed;
    private int _direction;
    private boolean _moving;
    private Keyboard input;
    private int timeBetweenPutBombs = 0;
    private int numberOfBomb = 1;
    private int flameLength = 1;

    public Bomber(int x, int y, Image img, Keyboard keyboard) {
        super(x, y, img);
        _moving = false;
        input = keyboard;
        solid = false;
        speed = Const.MOVINGSPEED;
        setH(44);
        setW(35);
    }

    @Override
    public void update() {
        animate();

        if (!_alive) {
            afterKill();
            return;
        }
        if (timeBetweenPutBombs < -7500) timeBetweenPutBombs = 0;
        else timeBetweenPutBombs--;
        move();
        changeImg();
        detectPlaceBomb();
        collide();
    }

    /**
     * Di chuyển Bomber.
     */
    public void move() //initiates/continues movement, should be called every frame
    {
        dx = 0;
        dy = 0;
        if (input.getActiveKeys().contains(KeyCode.W)) {
            dy -= speed;
            _direction = 0;
        } else if (input.getActiveKeys().contains(KeyCode.S)) {

            dy = speed;
            _direction = 1;
        } else if (input.getActiveKeys().contains(KeyCode.A)) {
            dx -= speed;
            _direction = 2;
        } else if (input.getActiveKeys().contains(KeyCode.D)) {
            dx = speed;
            _direction = 3;
        }

        if (dx != 0 || dy != 0) {
            _moving = true;

            if (canMove(dx, 0)) {
                BombermanGame.doCamera(dx);
                x += dx;
            }
            if (canMove(0, dy)) {
                y += dy;
            }
            ///System.out.println(String.format("Bomber: (" + x + ", " + y +")"));
        } else {
            _moving = false;
        }
    }

    private boolean canMove(float x, float y) {
        float xt = (this.x + x + 1);
        float yt = (this.y + y + 1);
        //System.out.println(String.format("X:" + xt + ", Y:" + yt));

        if (BombermanGame.wallCheck(xt, yt, this.w, this.h) ||
                BombermanGame.brickCheck(xt, yt, this.w, this.h, false) ||
                BombermanGame.bombCheck(xt, yt, this.w, this.h)) return false;
//            if (e instanceof Wall || e instanceof Brick) {
//                return false;
//            }
        return true;
    }

    /**
     * Đổi ảnh dựa trên hướng đi và animation.
     */
    private void changeImg() {
        switch (_direction) {
            case 0:
                img = Sprite.player_up.getFxImage();
                if (_moving) {
                    img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, _animate, 40).getFxImage();
                }
                break;
            case 1:
                img = Sprite.player_down.getFxImage();
                if (_moving) {
                    img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, _animate, 40).getFxImage();
                }
                break;
            case 2:
                img = Sprite.player_left.getFxImage();
                if (_moving) {
                    img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, _animate, 40).getFxImage();
                }
                break;
            case 3:
                img = Sprite.player_right.getFxImage();
                if (_moving) {
                    img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, _animate, 40).getFxImage();
                }
                break;
            default:
                img = Sprite.player_right.getFxImage();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (_alive) {
            changeImg();
        } else {
            if(_timeAfter > 0) {
                img = Sprite.player_dead1.getFxImage();
                _animate = 0;
            } else
                img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, _animate, 120).getFxImage();
        }
        gc.drawImage(img, x, y);
        //gc.strokeRect(x, y, 35, 44);
    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
        String musicFile = "res/WAV/death.wav";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            if(_finalAnimation > 0) --_finalAnimation;
            else {
                System.out.println("yes");
                remove();
                BombermanGame.setGameOver(true);
            }
        }
    }

    @Override
    public void collide() {
        Entity e = BombermanGame.getEntity(x, y, w, h);
        if (e instanceof Enemy) kill();
    }

    private void detectPlaceBomb() {
        if (input.getActiveKeys().contains(KeyCode.SPACE) && timeBetweenPutBombs < 0 && numberOfBomb > 0) {
            //System.out.println(i++);
            numberOfBomb--;
            int xb = Math.round(x / Sprite.SCALED_SIZE);
            int yb = Math.round(y / Sprite.SCALED_SIZE);
            placeBomb(xb, yb);
            timeBetweenPutBombs = 40;
        }
    }

    private void placeBomb(int xb, int yb) {
        Bomb b = new Bomb(xb, yb, Sprite.bomb.getFxImage(), this);
        BombermanGame.addBomb(b);
    }

    public int getNumberOfBomb() {
        return numberOfBomb;
    }

    public void increNumberOfBomb() {
        this.numberOfBomb++;
    }

    public int getFlameLength() {
        return flameLength;
    }

    public void increFlameLength() {
        flameLength++;
    }

    public void increSpeed() {
        speed += 1;
    }
}
