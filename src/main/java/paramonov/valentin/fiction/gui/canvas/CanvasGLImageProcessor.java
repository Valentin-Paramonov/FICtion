package paramonov.valentin.fiction.gui.canvas;

import paramonov.valentin.fiction.gl.processor.GLFramebufferProcessor;
import paramonov.valentin.fiction.gl.processor.GLImageProcessor;
import paramonov.valentin.fiction.gl.processor.GLTextureProcessor;

import javax.media.opengl.GL2;
import java.nio.IntBuffer;

import static javax.media.opengl.GL.GL_FRAMEBUFFER;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.GL2GL3.GL_QUADS;
import static javax.media.opengl.GL2GL3.GL_TEXTURE_HEIGHT;
import static javax.media.opengl.GL2GL3.GL_TEXTURE_WIDTH;

public class CanvasGLImageProcessor implements GLImageProcessor {
    private final GLTextureProcessor textureProcessor;
    private final GLFramebufferProcessor framebufferProcessor;

    public CanvasGLImageProcessor(GLTextureProcessor textureProcessor, GLFramebufferProcessor framebufferProcessor) {
        this.textureProcessor = textureProcessor;
        this.framebufferProcessor = framebufferProcessor;
    }

    @Override
    public void drawTexture(GL2 gl, int framebufferId, int textureId) {
        int oldTextureId = textureProcessor.getCurrentTextureId(gl);
        int oldBufferId =  framebufferProcessor.getCurrentFramebufferId(gl);

        gl.glBindTexture(GL_TEXTURE_2D, textureId);
        gl.glBindFramebuffer(GL_FRAMEBUFFER, framebufferId);

        IntBuffer params = IntBuffer.wrap(new int[2]);

        gl.glGetTexLevelParameteriv(GL_TEXTURE_2D, 0, GL_TEXTURE_WIDTH, params);

        params.position(1);

        gl.glGetTexLevelParameteriv(GL_TEXTURE_2D, 0, GL_TEXTURE_HEIGHT, params);

        int texWidth = params.get(0);
        int texHeight = params.get(1);

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

        gl.glBindFramebuffer(GL_FRAMEBUFFER, oldBufferId);
        gl.glBindTexture(GL_TEXTURE_2D, oldTextureId);
    }
}
