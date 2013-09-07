package paramonov.valentin.fiction.gui.canvas;

import paramonov.valentin.fiction.gui.action.canvas.CanvasAction;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;

import static javax.media.opengl.GL2.GL_DEPTH_TEST;
import static javax.media.opengl.GL2.GL_TEXTURE_2D;
import static paramonov.valentin.fiction.gui.action.canvas.CanvasAction.CANVAS_NO_ACTION;

public class AppGLCanvas
    extends GLCanvas implements GLEventListener {

    public static final long serialVersionUID = 0xf00d;
    private CanvasAction action;

    public AppGLCanvas() {
        addGLEventListener(this);
        action = CANVAS_NO_ACTION;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glDisable(GL_DEPTH_TEST);
        gl.glEnable(GL_TEXTURE_2D);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        switch(action) {
            case CANVAS_LOAD_IMAGE:
                System.out.println("Load to canvas.");
                break;
        }

        action = CANVAS_NO_ACTION;
    }

    @Override
    public void reshape(
        GLAutoDrawable glAutoDrawable,
        int x, int y, int wdrw, int hdrw) {

        GL2 gl = glAutoDrawable.getGL().getGL2();
    }

    public void performAction(CanvasAction action) {
        this.action = action;
        display();
    }
}
