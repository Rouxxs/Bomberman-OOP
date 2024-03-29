package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Const;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;

public class Bomb extends AnimatedEntity {
    private double timeToExplode = 180;
    private int timeAfter = 60;
    private boolean exploded = false;
    private Flame[] flames;
    private Bomber bomberman;


    public Bomb(float xUnit, float yUnit, Image img, Bomber b) {
        super(xUnit, yUnit, img);
        bomberman = b;
    }

    @Override
    public void update() {
        float xt = bomberman.getX();
        float yt = bomberman.getY();

        if (!Const.collision(xt, yt, 35, 44, x, y, 48, 48)) {
            solid = true;
        }

        if (timeToExplode > 0) {
            timeToExplode--;
        }
        else {
            if (!exploded) {
                explode();
            }

            if (timeAfter > 0) {
                timeAfter--;
            } else {
                remove();
            }
        }


        animate();
    }

    @Override
    public void collide() {
        return;
    }

    @Override
    public void render(GraphicsContext gc) {
        if(exploded) {
            img = Sprite.bomb_exploded2.getFxImage();
            renderFlame(gc);
        } else {
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60).getFxImage();

        }
        gc.drawImage(img, x, y);
        /*String musicFile = "res/WAV/setbom.wav";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();*/

    }

    private void explode() {
        exploded = true;
        BombermanGame.getBomberman().increNumberOfBomb();
        String musicFile = "res/WAV/explosion.wav";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        flames = new Flame[4];
        for (int i = 0; i < 4; i++) {
            flames[i] = new Flame((int) x / 48, (int) y / 48, i, bomberman.getFlameLength());
        }
    }

    private void renderFlame(GraphicsContext gc) {
        for (int i = 0; i < flames.length; i++) {
            flames[i].render(gc);
        }
    }
    @Override
    public void kill() {

    }

    @Override
    protected void afterKill() {

    }

    public void setTimeToExplode(double timeToExplode) {
        this.timeToExplode = timeToExplode;
    }
}
