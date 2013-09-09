package paramonov.valentin.fiction.gui.canvas;

import paramonov.valentin.fiction.gui.action.Action;
import paramonov.valentin.fiction.gui.canvas.operator.exception.NumberOfArgumentsOperationException;
import paramonov.valentin.fiction.gui.canvas.operator.exception.OperationException;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.processor.ImageProcessor;
import paramonov.valentin.fiction.image.processor.ImageProcessorProvider;

import java.io.FileNotFoundException;

import static paramonov.valentin.fiction.gui.action.canvas.CanvasAction.CANVAS_LOAD_IMAGE;
import static paramonov.valentin.fiction.gui.action.canvas.CanvasAction.CANVAS_REPAINT;

public class CanvasOperator {
    private AppGLCanvas canvas;

    CanvasOperator() {}

    void setCanvas(AppGLCanvas canvas) {
        this.canvas = canvas;
    }

    public void operate(Action action, String ... args)
        throws OperationException {

        switch(action) {
            case LOAD_IMAGE:
                canvasLoadImage(args);
                break;

            case UPDATE_GRAPHICS:
                canvasUpdate();
                break;
        }
    }

    private void canvasUpdate()
        throws OperationException {

        if(!canvas.isReady()) {
            throw new OperationException(
                "No image loaded to the canvas!");
        }

        canvas.performAction(CANVAS_REPAINT);
    }

    private void checkArgCount(int argCount, int expected)
        throws NumberOfArgumentsOperationException {

        if(argCount != expected) {
            throw new NumberOfArgumentsOperationException(
                String.format(
                    "Expected: %d, got: %d.\n",
                    expected, argCount));
        }
    }

    private void canvasLoadImage(String[] args)
        throws OperationException {

        checkArgCount(args.length, 1);

        ImageProcessor processor =
            ImageProcessorProvider.getImageProcessor();

        Image img;

        try {
            img = processor.loadImageFromFile(args[0]);
        } catch (FileNotFoundException e) {
            throw new OperationException(e.getMessage());
        }

        canvas.setImageToLoad(img);
        canvas.performAction(CANVAS_LOAD_IMAGE);
    }
}
