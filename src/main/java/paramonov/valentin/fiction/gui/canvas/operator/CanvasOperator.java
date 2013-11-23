package paramonov.valentin.fiction.gui.canvas.operator;

import paramonov.valentin.fiction.gui.canvas.operator.exception.OperationException;

public interface CanvasOperator {
    void loadImage(String image) throws OperationException;

    void update();
}
