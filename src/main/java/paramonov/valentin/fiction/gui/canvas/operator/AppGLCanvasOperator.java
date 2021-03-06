package paramonov.valentin.fiction.gui.canvas.operator;

import paramonov.valentin.fiction.gui.canvas.operator.exception.OperationException;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.processor.ImageProcessor;

import java.io.IOException;

import static paramonov.valentin.fiction.gui.canvas.action.CanvasAction.*;

public class AppGLCanvasOperator implements CanvasOperator {
    private OperatableCanvas canvas;

    AppGLCanvasOperator() {}

    void setCanvas(OperatableCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void loadImage(String imgFile) throws OperationException {
        Image img;

        try {
            img = ImageProcessor.loadImageFromFile(imgFile);
        } catch(IOException e) {
            throw new OperationException(e.getMessage());
        }

        canvas.setImageToLoad(img);
        canvas.performAction(CANVAS_LOAD_IMAGE);
    }

    @Override
    public void update() {
        if(!canvas.isReady()) {
            canvas.performAction(CANVAS_CLEAR);
            return;
        }

        canvas.performAction(CANVAS_REPAINT);
    }
}
