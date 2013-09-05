package paramonov.valentin.fiction.gui.canvas;

import paramonov.valentin.fiction.gui.action.Action;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppGLCanvas
    extends GLCanvas implements GLEventListener, ActionListener {

    public static final long serialVersionUID = 0xf00d;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
    }

    @Override
    public void reshape(
        GLAutoDrawable glAutoDrawable,
        int x, int y, int wdrw, int hdrw) {

        GL2 gl = glAutoDrawable.getGL().getGL2();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String comm = ae.getActionCommand();
        Action act;

        try {
            act = Action.valueOf(comm);
        } catch(IllegalArgumentException iae) {
            System.out.println("No such action: " + comm);
            return;
        }

        switch(act) {
            case LOAD_IMAGE:
                System.out.println("Load image.");
                break;
        }
    }
}
