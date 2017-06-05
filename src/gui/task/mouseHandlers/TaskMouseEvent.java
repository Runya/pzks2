package gui.task.mouseHandlers;

/**
 * Created by hadgehog on 18.02.14.
 */
public class TaskMouseEvent {
    private MouseEventType type;
    private int x;
    private int y;

    public TaskMouseEvent(MouseEventType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public MouseEventType getType() {
        return type;
    }

    public enum MouseEventType {
        PRESSED, RELEASED, MOVE, ENTERED, EXITED, DOUBLE_CLICK
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
