package uet.oop.bomberman.input;


import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

public class Keyboard implements EventHandler<KeyEvent> {
    private Set<KeyCode> activeKeys = new HashSet<>();

    @Override
    public void handle(KeyEvent event) {
        if (KeyEvent.KEY_PRESSED.equals(event.getEventType())) {
            activeKeys.add(event.getCode());
            //System.out.println(event.getCode());
        } else if (KeyEvent.KEY_RELEASED.equals(event.getEventType())) {
            activeKeys.remove(event.getCode());
        }
    }

    public Set<KeyCode> getActiveKeys() {
        return activeKeys;
    }


}
