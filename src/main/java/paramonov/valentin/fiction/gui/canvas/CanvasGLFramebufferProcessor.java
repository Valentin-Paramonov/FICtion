package paramonov.valentin.fiction.gui.canvas;

import com.jogamp.common.nio.Buffers;
import paramonov.valentin.fiction.gui.gl.processor.GLFramebufferProcessor;
import paramonov.valentin.fiction.gui.gl.processor.GLTextureProcessor;
import paramonov.valentin.fiction.gui.gl.processor.InvalidBufferSizeException;

import javax.media.opengl.GL2;
import java.nio.ByteBuffer;

import static javax.media.opengl.GL2.*;

public class CanvasGLFramebufferProcessor implements GLFramebufferProcessor {
    final GLTextureProcessor textureProcessor;

    public CanvasGLFramebufferProcessor(GLTextureProcessor textureProcessor) {
        this.textureProcessor = textureProcessor;
    }

    @Override
    public int createRGBAFramebuffer(GL2 gl, int w, int h, ByteBuffer buffer) throws InvalidBufferSizeException {
        if(buffer.array().length != w * h * 4) {
            throw new InvalidBufferSizeException();
        }

        final int id;
        int oldTextureId = textureProcessor.getCurrentTextureId(gl);
        int oldFramebufferId = getCurrentFramebufferId(gl);

        int backingTexture = textureProcessor.createTexture(gl);

        gl.glBindTexture(GL_TEXTURE_2D, backingTexture);
        gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w, h, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        gl.glBindTexture(GL_TEXTURE_2D, oldTextureId);

        id = genFramebufferId(gl);

        gl.glBindFramebuffer(GL_FRAMEBUFFER, id);
        gl.glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, backingTexture, 0);
        gl.glBindFramebuffer(GL_FRAMEBUFFER, oldFramebufferId);

        return id;
    }

    @Override
    public int createGreyFramebuffer(GL2 gl, int w, int h, ByteBuffer buffer) throws InvalidBufferSizeException {
        if(buffer.array().length != w * h) {
            throw new InvalidBufferSizeException();
        }

        int oldTextureId = textureProcessor.getCurrentTextureId(gl);
        int oldFramebufferId = getCurrentFramebufferId(gl);
        final int id = genFramebufferId(gl);

        int backingTexture = textureProcessor.createTexture(gl);

        gl.glBindTexture(GL_TEXTURE_2D, backingTexture);
        gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_R, w, h, 0, GL_R, GL_UNSIGNED_BYTE, buffer);
        gl.glBindTexture(GL_TEXTURE_2D, oldTextureId);

        gl.glBindFramebuffer(GL_FRAMEBUFFER, id);
        gl.glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, backingTexture, 0);
        gl.glBindFramebuffer(GL_FRAMEBUFFER, oldFramebufferId);

        return id;
    }

    @Override
    public int createRGBAFramebuffer(GL2 gl, int w, int h) {
        ByteBuffer buffer = Buffers.newDirectByteBuffer(w * h * 4);
        try {
            return createRGBAFramebuffer(gl, w, h, buffer);
        } catch(InvalidBufferSizeException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int createGreyFramebuffer(GL2 gl, int w, int h) {
        ByteBuffer buffer = Buffers.newDirectByteBuffer(w * h);
        try {
            return createRGBAFramebuffer(gl, w, h, buffer);
        } catch(InvalidBufferSizeException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getCurrentFramebufferId(GL2 gl) {
        final int[] id = new int[1];

        gl.glGetIntegerv(GL_FRAMEBUFFER_BINDING, id, 0);

        return id[0];
    }

    @Override
    public int genFramebufferId(GL2 gl) {
        final int[] id = new int[1];

        gl.glGenFramebuffers(1, id, 0);

        return id[0];
    }
}
