package paramonov.valentin.fiction.gui.canvas.operator;

import paramonov.valentin.fiction.gui.action.Action;
import paramonov.valentin.fiction.gui.canvas.AppGLCanvas;

import static paramonov.valentin.fiction.gui.action.canvas.CanvasAction.CANVAS_LOAD_IMAGE;

public class CanvasOperator {
    private AppGLCanvas canvas;

    CanvasOperator() {}

    void setCanvas(AppGLCanvas canvas) {
        this.canvas = canvas;
    }

    public void operate(Action action, String ... args) {
        switch(action) {
            case LOAD_IMAGE:
                canvasLoadImage();
                break;
        }
    }

    private void canvasLoadImage() {
        canvas.performAction(CANVAS_LOAD_IMAGE);
    }
}
