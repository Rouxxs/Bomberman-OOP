package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Const;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;

import java.io.File;

import static uet.oop.bomberman.BombermanGame.bomberman;

public class BombItem extends Item {
    public BombItem(int x, int y, Image image) {
        super(x, y, image);
    }

    @Override
    public void update() {
        if (Const.collision(x, y, w, h, bomberman.getX(), bomberman.getY(), bomberman.getW(),bomberman.getH())) {
            if (!this.isRemoved()) {
                String musicFile = "res/WAV/pickup.wav";     // For example

                Media sound = new Media(new File(musicFile).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                bomberman.increNumberOfBomb();
                remove();
            }
        }
    }
}
