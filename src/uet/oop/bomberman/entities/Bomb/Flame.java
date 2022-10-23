package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {
    protected int direction;
    private int length;
    protected int startX, startY;
    protected FlameSegment[] flameSegments = new FlameSegment[0];

    public Flame(int x, int y, int direction, int length) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.startX = x;
        this.startY = y;
        this.direction = direction;
        this.length = length;
        createFlameSegments();
    }

    private void createFlameSegments() {
        flameSegments = new FlameSegment[calculateLength()];
        //System.out.println(calculateLength());
        boolean last;
        int xt = startX;
        int yt = startY;
        for (int i = 0; i < flameSegments.length; i++) {
            if (i == flameSegments.length - 1) last = true;
            else last = false;

            switch (direction)
            {
                case 0: yt--; break;
                case 1: yt++; break;
                case 2: xt--; break;
                case 3: xt++; break;
            }

            flameSegments[i] = new FlameSegment(xt, yt, direction, last);
        }
    }

    private int calculateLength() {
        int l = 0;
        float dx = x;
        float dy = y;

        while (l < length) {
            switch (direction) {
                case 0:
                    dy -= 32;
                    break;
                case 1:
                    dy += 32;
                    break;
                case 2:
                    dx -= 32;
                    break;
                case 3:
                    dx += 32;
                    break;
            }

            if (BombermanGame.wallCheck(dx, dy, this.w, this.h) ||
                    BombermanGame.brickCheck(dx, dy, this.w, this.h, true)) break;
            Entity e = BombermanGame.getEntity(dx, dy, this.w, this.h);
            if (e instanceof AnimatedEntity) {
                ((AnimatedEntity) e).kill();
                if (e instanceof Enemy) {
                    BombermanGame.doPoint(((Enemy) e).getPoints());
                }
            }
            if (e instanceof Bomb) ((Bomb) e).setTimeToExplode(-1);
            l++;
        }
        return l;
    }

    @Override
    public void update() {

    }

    @Override
    public void collide() {
        return;
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < flameSegments.length; i++) {
            flameSegments[i].render(gc);
        }
    }
}
