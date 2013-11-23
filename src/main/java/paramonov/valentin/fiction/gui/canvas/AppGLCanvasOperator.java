package paramonov.valentin.fiction.gui.canvas;

import paramonov.valentin.fiction.gui.canvas.operator.CanvasOperator;
import paramonov.valentin.fiction.gui.canvas.operator.exception.OperationException;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.processor.ImageProcessor;
import paramonov.valentin.fiction.image.processor.ImageProcessorProvider;

import java.io.IOException;

import static paramonov.valentin.fiction.gui.action.canvas.CanvasAction.*;

public class AppGLCanvasOperator implements CanvasOperator {
    private AppGLCanvas canvas;

    AppGLCanvasOperator() {}

    void setCanvas(AppGLCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void loadImage(String imgFile) throws OperationException {
        ImageProcessor processor =
            ImageProcessorProvider.getImageProcessor();

        Image img;

        try {
            img = processor.loadImageFromFile(imgFile);
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
