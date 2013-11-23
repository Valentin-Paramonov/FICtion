package paramonov.valentin.fiction.gui.canvas;

import paramonov.valentin.fiction.gui.canvas.operator.CanvasOperator;

public class CanvasOperatorFactory {
    private CanvasOperatorFactory() {}

    public static CanvasOperator
    createCanvasOperator(AppGLCanvas canvas) {

        AppGLCanvasOperator operator = new AppGLCanvasOperator();

        operator.setCanvas(canvas);

        return operator;
    }
}
