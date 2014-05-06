package paramonov.valentin.fiction.gui.canvas;

import paramonov.valentin.fiction.gl.processor.GLFramebufferProcessor;
import paramonov.valentin.fiction.gl.processor.GLImageProcessor;
import paramonov.valentin.fiction.gl.processor.GLTextureProcessor;
import paramonov.valentin.fiction.gui.canvas.action.CanvasAction;
import paramonov.valentin.fiction.gui.canvas.operator.OperatableCanvas;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.image.processor.ImageProcessor;
import paramonov.valentin.fiction.image.processor.ImageProcessorProvider;
import paramonov.valentin.fiction.transformation.GLTransformator;
import paramonov.valentin.fiction.transformation.Rotate90CWGLTransform;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.awt.GLCanvas;

import java.io.IOException;

import static javax.media.opengl.GL2.*;
import static paramonov.valentin.fiction.gui.canvas.action.CanvasAction.CANCAS_TRANSFORM;
import static paramonov.valentin.fiction.gui.canvas.action.CanvasAction.CANVAS_NO_ACTION;

public class AppGLCanvas extends GLCanvas implements GLEventListener, OperatableCanvas {
    public static final long serialVersionUID = 0xf00d;
    private static final int DEFAULT_TEXTURE = 0;

    private CanvasAction action;

    private Image imageToLoad;
    private boolean ready;
    private int currentTexture;
    private final GLTextureProcessor textureProcessor;
    private final GLFramebufferProcessor framebufferProcessor;
    private final GLImageProcessor imageProcessor;

//    private GLTransformator[] transformators;
//    private Image transformImage;
//    private byte[][] imageTransformations;

    public AppGLCanvas(GLTextureProcessor textureProcessor, GLFramebufferProcessor framebufferProcessor,
        GLImageProcessor imageProcessor) throws GLException {

        this.textureProcessor = textureProcessor;
        this.framebufferProcessor = framebufferProcessor;
        this.imageProcessor = imageProcessor;

        addGLEventListener(this);
        action = CANVAS_NO_ACTION;
        setReadyState(false);
        setCurrentTexture(DEFAULT_TEXTURE);
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glDisable(GL_DEPTH_TEST);
        gl.glEnable(GL_TEXTURE_2D);

        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL_BLEND);

        clear(gl);

//        transformTest();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        clear(gl);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        switch(action) {
            case CANVAS_LOAD_IMAGE:
                createImageTexture(gl);
                break;

            case CANVAS_REPAINT:
                imageProcessor.drawTexture(gl, 0, currentTexture);
                break;

            case CANVAS_CLEAR:
                clear(gl);
                break;

//            case CANCAS_TRANSFORM:
//                performTransformations(gl);
//                break;
        }

        action = CANVAS_NO_ACTION;
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int wdrw, int hdrw) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        clear(gl);
    }

    private double aspect() {
        int h = getHeight();

        return (double) getWidth() / (h != 0 ? h : 1);
    }

    @Override
    public void performAction(CanvasAction action) {
        this.action = action;
        display();
    }

    @Override
    public void setImageToLoad(Image imageToLoad) {
        this.imageToLoad = imageToLoad;
    }

    private void createImageTexture(GL2 gl) {
        int textureId = textureProcessor.createTextureFromImage(gl, imageToLoad);
        setCurrentTexture(textureId);

        displayImageTexture(gl, textureId, imageToLoad);

        setReadyState(true);
    }

    private void setReadyState(boolean state) {
        ready = state;
    }

    private void setCurrentTexture(int texId) {
        currentTexture = texId;
    }

    private void displayImageTexture(GL2 gl, int texId, Image img) {
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();

        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glViewport(0, 0, getWidth(), getHeight());
        gl.glOrtho(0, imgWidth, imgHeight, 0, 0, 1);

        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();

        clear(gl);

        scaleImage(gl, img);

        imageProcessor.drawTexture(gl, 0, texId);
    }

    private void scaleImage(GL2 gl, Image img) {
        double imgAspect = img.getAspect();
        double canvasAspect = aspect();

        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();

        if(imgAspect > canvasAspect) {
            gl.glTranslatef(0, (float) imgHeight / 2, 0);
            gl.glScaled(1, canvasAspect / imgAspect, 1);
            gl.glTranslatef(0, (float) -imgHeight / 2, 0);
        } else {
            gl.glTranslatef((float) imgWidth / 2, 0, 0);
            gl.glScaled(imgAspect / canvasAspect, 1, 1);
            gl.glTranslatef((float) -imgWidth / 2, 0, 0);
        }
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    void clear(GL2 gl) {
        gl.glDisable(GL_BLEND);
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_ACCUM_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        gl.glEnable(GL_BLEND);
    }

//    protected void initTransformators(GL2 gl, int width, int height) {
//        transformators = new GLTransformator[]{
//            new Rotate90CWGLTransform(gl, width, height, framebufferProcessor, textureProcessor, imageProcessor)
//        };
//    }
//
//    private void performTransformations(GL2 gl) {
//        initTransformators(gl, transformImage.getWidth(), transformImage.getHeight());
//        imageTransformations = new byte[transformators.length][];
//        int textureId = textureProcessor
//            .createTextureFromByteArray(gl, transformImage.getWidth(), transformImage.getHeight(),
//                transformImage.getBlueChannel());
//
//        for(int i = 0; i < transformators.length; i++) {
//            imageTransformations[i] = transformators[i].transform(gl, textureId);
//        }
//
//        gl.glDeleteTextures(1, new int[]{textureId}, 0);
//    }

//    public byte[][] transform(Image img) {
//        transformImage = img;
//
//        action = CANCAS_TRANSFORM;
//        display();
//
//        transformImage = null;
//
//        byte[][] transformations = imageTransformations;
//        imageTransformations = null;
//
//        return transformations;
//    }
//
//    private void transformTest() {
//        ImageProcessor proc = ImageProcessorProvider.getImageProcessor();
//        Image img;
//        try {
//            img = proc.loadImageFromFile("src/test/resources/" + "lenna.png");
//        } catch(IOException e) {
//            e.printStackTrace();
//            return;
//        }
//        Image grayImg = proc.toGrayscale(img);
//        byte[][] transforms = transform(grayImg);
//
//        int[] color = new int[transforms[0].length];
//
//        for(int i = 0; i < transforms[0].length; i++) {
//            color[i] = transforms[0][i];
//        }
//
//        Image out = new Image(color, grayImg.getWidth(), grayImg.getHeight());
//
//        try {
//            proc.writeImageToFile(out, "/home/valentine/tttest.png");
//        } catch(IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }
}
