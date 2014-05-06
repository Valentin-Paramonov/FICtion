package paramonov.valentin.fiction.gui.canvas;

import org.junit.Before;
import org.junit.Test;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CanvasGLTextureProcessorTest {
    private GLDrawable canvas;
    private int indicator = 0;

    @Before
    public void setUp() {
//        canvas = GLDrawableFactory.getFactory(GLProfile.getDefault()).createGLCanvas(new GLCapabilities(GLProfile.getDefault()));
        canvas = GLDrawableFactory.getFactory(GLProfile.getDefault()).createExternalGLDrawable();
    }

    @Test
    public void testListener_AcceptsEvents() throws Exception {
    }

    @Test
    public void testGetTextureDims() throws Exception {

    }
}
