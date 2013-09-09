package paramonov.valentin.fiction.gui.canvas;

public class CanvasOperatorFactory {
    private CanvasOperatorFactory() {}

    public static CanvasOperator
        createCanvasOperator(AppGLCanvas canvas) {

        CanvasOperator operator = new CanvasOperator();

        operator.setCanvas(canvas);

        return operator;
    }
}
