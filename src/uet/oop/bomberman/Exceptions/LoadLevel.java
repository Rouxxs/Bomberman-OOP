package uet.oop.bomberman.Exceptions;

public class LoadLevel extends GameException {
    public LoadLevel() {
    }

    public LoadLevel(String str) {
        super(str);

    }

    public LoadLevel(String str, Throwable cause) {
        super(str, cause);

    }

    public LoadLevel(Throwable cause) {
        super(cause);

    }

}
