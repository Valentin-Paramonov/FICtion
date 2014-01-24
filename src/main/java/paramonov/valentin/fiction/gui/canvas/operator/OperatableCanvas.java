package paramonov.valentin.fiction.gui.canvas.operator;

import paramonov.valentin.fiction.gui.canvas.action.CanvasAction;
import paramonov.valentin.fiction.image.Image;

public interface OperatableCanvas {
    boolean isReady();
    void setImageToLoad(Image img);
    void performAction(CanvasAction action);
}
