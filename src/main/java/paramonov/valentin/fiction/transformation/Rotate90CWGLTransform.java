package paramonov.valentin.fiction.transformation;

import paramonov.valentin.fiction.gl.processor.GLFramebufferProcessor;
import paramonov.valentin.fiction.gl.processor.GLImageProcessor;
import paramonov.valentin.fiction.gl.processor.GLTextureProcessor;

import javax.media.opengl.GL2;
import static javax.media.opengl.GL2.*;

public class Rotate90CWGLTransform extends AffineGLTransformator {
    public Rotate90CWGLTransform(GL2 gl, int width, int height,
        GLFramebufferProcessor framebufferProcessor,
        GLTextureProcessor textureProcessor,
        GLImageProcessor imageProcessor) {

        super(gl, width, height, framebufferProcessor, textureProcessor, imageProcessor);
    }

    @Override
    public byte[] transform(GL2 gl, int textureId) {
        int oldFramebuffer = framebufferProcessor.getCurrentFramebufferId(gl);
        gl.glBindFramebuffer(GL_FRAMEBUFFER, framebufferId);

        gl.glPushMatrix();

        gl.glTranslated(width / 2., height / 2., 0);
        gl.glRotated(90, 0, 0, 1);
        gl.glTranslated(-width / 2., -height / 2., 0);
        imageProcessor.drawTexture(gl, framebufferId, textureId);

        gl.glPopMatrix();

        gl.glBindFramebuffer(GL_FRAMEBUFFER, oldFramebuffer);

        return readFramebufferContents(gl);
    }
}
