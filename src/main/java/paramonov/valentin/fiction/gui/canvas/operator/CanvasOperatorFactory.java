package paramonov.valentin.fiction.gui.canvas.operator;

import paramonov.valentin.fiction.gui.canvas.AppGLCanvas;

public class CanvasOperatorFactory {
    private CanvasOperatorFactory() {}

    public static CanvasOperator
    createCanvasOperator(AppGLCanvas canvas) {

        AppGLCanvasOperator operator = new AppGLCanvasOperator();

        operator.setCanvas(canvas);

        return operator;
    }
}
