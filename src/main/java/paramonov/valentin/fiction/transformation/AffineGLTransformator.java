package paramonov.valentin.fiction.transformation;

import paramonov.valentin.fiction.gl.processor.GLFramebufferProcessor;
import paramonov.valentin.fiction.gl.processor.GLImageProcessor;
import paramonov.valentin.fiction.gl.processor.GLTextureProcessor;

import javax.media.opengl.GL2;

public abstract class AffineGLTransformator implements GLTransformator {
    protected final int width;
    protected final int height;
    protected final GLFramebufferProcessor framebufferProcessor;
    protected final GLTextureProcessor textureProcessor;
    protected final GLImageProcessor imageProcessor;
    protected final int framebufferId;

    protected AffineGLTransformator(GL2 gl, int width, int height,
        GLFramebufferProcessor framebufferProcessor,
        GLTextureProcessor textureProcessor, GLImageProcessor imageProcessor) {

        this.width = width;
        this.height = height;
        this.framebufferProcessor = framebufferProcessor;
        this.textureProcessor = textureProcessor;
        this.imageProcessor = imageProcessor;

        framebufferId = init(gl);
    }

    private int init(GL2 gl) {
         return framebufferProcessor.createRGBAFramebuffer(gl, width, height);
    }

    protected byte[] readFramebufferContents(GL2 gl) {
         return framebufferProcessor.getFramebufferContentsRGBA(gl, framebufferId, width, height);
    }
}
