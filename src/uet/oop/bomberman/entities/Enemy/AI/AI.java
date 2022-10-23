package uet.oop.bomberman.entities.Enemy.AI;

public abstract class AI {
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public abstract int calculateDirection();
}
