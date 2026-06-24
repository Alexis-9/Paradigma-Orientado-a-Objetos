package main.Controller;

public interface InputSource {
    boolean isUpPressed();
    boolean isDownPressed();
    boolean isLeftPressed();
    boolean isRightPressed();
    boolean consumeEscPress();
    boolean consumeEnterPress();
    Boolean consumeMutePress();
}
