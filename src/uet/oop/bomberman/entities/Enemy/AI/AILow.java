package uet.oop.bomberman.entities.Enemy.AI;

import uet.oop.bomberman.entities.Enemy.AI.AI;

public class AILow extends AI {

    @Override
    public int calculateDirection() {
        int i;
        i = getRandomNumber(0, 4);
        return i;
    }


}