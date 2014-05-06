package paramonov.valentin.fiction;

import org.junit.Before;
import org.junit.Test;

import javax.media.opengl.GLContext;
import javax.media.opengl.GLDrawableFactory;
import javax.media.opengl.GLProfile;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GLTest {
    private GLProfile profile;
    private GLDrawableFactory factory;

    @Before
    public void setUp() throws Exception {
        profile = GLProfile.get(GLProfile.GL2);
        factory = GLDrawableFactory.getFactory(profile);
    }

    @Test
    public void testCreateExternalGlContext_NotNull() {
        GLContext context = factory.createExternalGLContext();

        assertThat(context, notNullValue());
    }
}
