package paramonov.valentin.fiction.gui.canvas;

import paramonov.valentin.fiction.gui.action.canvas.CanvasAction;
import paramonov.valentin.fiction.image.Image;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import java.nio.IntBuffer;

import static javax.media.opengl.GL2.*;
import static paramonov.valentin.fiction.gui.action.canvas.CanvasAction.CANVAS_NO_ACTION;

public class AppGLCanvas
    extends GLCanvas implements GLEventListener {

    public static final long serialVersionUID = 0xf00d;
    private static final int DEFAULT_TEXTURE = 0;

    private CanvasAction action;

    private Image imageToLoad;
    private boolean ready;
    private int currentTexture;

    public AppGLCanvas() {
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
                drawImage(gl, currentTexture);
                break;

            case CANVAS_CLEAR:
                clear(gl);
                break;
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

    void performAction(CanvasAction action) {
        this.action = action;
        display();
    }

    void setImageToLoad(Image imageToLoad) {
        this.imageToLoad = imageToLoad;
    }

    private void createImageTexture(GL2 gl) {
        int texId = createTexture(gl);
        loadImage2Texture(gl, texId, imageToLoad);
        displayImageTexture(gl, texId, imageToLoad);

        setCurrentTexture(texId);
        setReadyState(true);
    }

    private void setReadyState(boolean state) {
        ready = state;
    }

    private void setCurrentTexture(int texId) {
        currentTexture = texId;
    }

    private int genTextureID(GL2 gl) {
        final int[] id = new int[1];

        gl.glGenTextures(1, id, 0);

        return id[0];
    }

    private int createTexture(GL2 gl) {
        int id;

        id = genTextureID(gl);

        gl.glBindTexture(GL_TEXTURE_2D, id);

        gl.glPixelStorei(GL_UNPACK_ALIGNMENT, 4);

        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        gl.glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);

        gl.glBindTexture(GL_TEXTURE_2D, DEFAULT_TEXTURE);

        return id;
    }

    private void loadImage2Texture(
        GL2 gl, int texId, Image imageToLoad) {

        gl.glBindTexture(GL_TEXTURE_2D, texId);

        gl.glTexImage2D(
            GL_TEXTURE_2D, 0, GL_RGBA,
            imageToLoad.getWidth(), imageToLoad.getHeight(),
            0, GL_BGRA, GL_UNSIGNED_INT_8_8_8_8_REV,
            imageToLoad.getBuffer()
        );

        gl.glBindTexture(GL_TEXTURE_2D, DEFAULT_TEXTURE);
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

        drawImage(gl, texId);
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

    private void drawImage(GL2 gl, int texId) {
        gl.glBindTexture(GL_TEXTURE_2D, texId);

        IntBuffer params = IntBuffer.wrap(new int[2]);

        gl.glGetTexLevelParameteriv(
            GL_TEXTURE_2D, 0, GL_TEXTURE_WIDTH, params);

        params.position(1);

        gl.glGetTexLevelParameteriv(
            GL_TEXTURE_2D, 0, GL_TEXTURE_HEIGHT, params);

        int texWidth = params.get(0);
        int texHeight = params.get(1);

        clear(gl);
        gl.glBegin(GL_QUADS);

//        gl.glTexCoord2d(0, 1);
        gl.glTexCoord2d(0, 0);
        gl.glVertex2d(0, 0);

//        gl.glTexCoord2d(0, 0);
        gl.glTexCoord2d(0, 1);
        gl.glVertex2d(0, texHeight);

//        gl.glTexCoord2d(1, 0);
        gl.glTexCoord2d(1, 1);
        gl.glVertex2d(texWidth, texHeight);

//        gl.glTexCoord2d(1, 1);
        gl.glTexCoord2d(1, 0);
        gl.glVertex2d(texWidth, 0);

        gl.glEnd();
        gl.glFlush();

        gl.glBindTexture(GL_TEXTURE_2D, DEFAULT_TEXTURE);
    }

    public boolean isReady() {
        return ready;
    }

    protected void clear(GL2 gl) {
        gl.glDisable(GL_BLEND);
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_ACCUM_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
        gl.glEnable(GL_BLEND);
    }
}
