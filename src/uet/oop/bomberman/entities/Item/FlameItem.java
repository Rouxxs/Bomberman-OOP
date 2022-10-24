package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.Const;

import java.io.File;

import static uet.oop.bomberman.BombermanGame.bomberman;

public class FlameItem extends Item {
    public FlameItem(int x, int y, Image image) {
        super(x, y, image);
    }

    public void update() {
        if (Const.collision(x, y, w, h, bomberman.getX(), bomberman.getY(), bomberman.getW(),bomberman.getH())) {
            if (!this.isRemoved()) {
                String musicFile = "res/WAV/pickup.wav";     // For example

                Media sound = new Media(new File(musicFile).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
                bomberman.increFlameLength();
                remove();
            }
        }
        return;
    }
}
