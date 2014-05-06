package paramonov.valentin.fiction.gui.canvas;

import com.jogamp.common.nio.Buffers;
import paramonov.valentin.fiction.collections.Pair;
import paramonov.valentin.fiction.gl.processor.GLTextureProcessor;
import paramonov.valentin.fiction.image.Image;

import javax.media.opengl.GL2;

import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2.GL_CLAMP;
import static javax.media.opengl.GL2.GL_R;
import static javax.media.opengl.GL2ES1.GL_TEXTURE_ENV;
import static javax.media.opengl.GL2ES1.GL_TEXTURE_ENV_MODE;
import static javax.media.opengl.GL2GL3.GL_TEXTURE_HEIGHT;
import static javax.media.opengl.GL2GL3.GL_TEXTURE_WIDTH;
import static javax.media.opengl.GL2GL3.GL_UNSIGNED_INT_8_8_8_8_REV;

public class CanvasGLTextureProcessor implements GLTextureProcessor {
    @Override
    public int genTextureId(GL2 gl) {
        final int[] id = new int[1];

        gl.glGenTextures(1, id, 0);

        return id[0];
    }

    @Override
    public int getCurrentTextureId(GL2 gl) {
        final int[] id = new int[1];

        gl.glGetIntegerv(GL_TEXTURE_BINDING_2D, id, 0);

        return id[0];
    }

    @Override
    public int createTexture(GL2 gl) {
        int oldTextureId = getCurrentTextureId(gl);
        int newTextureId = genTextureId(gl);

        gl.glBindTexture(GL_TEXTURE_2D, newTextureId);

        gl.glPixelStorei(GL_UNPACK_ALIGNMENT, 4);

        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        gl.glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);

        gl.glBindTexture(GL_TEXTURE_2D, oldTextureId);

        return newTextureId;
    }

    @Override
    public int createTextureFromImage(GL2 gl, Image img) {
        int textureId = createTexture(gl);

        loadImageToTexture(gl, textureId, img);

        return textureId;
    }

    @Override
    public int createTextureFromByteArray(GL2 gl, int w, int h, byte[] image) {
        int textureId = createTexture(gl);

        loadByteArrayToTexture(gl, textureId, w, h, image);

        return textureId;
    }

    @Override
    public void loadImageToTexture(GL2 gl, int textureId, Image img) {
        int oldTextureId = getCurrentTextureId(gl);

        gl.glBindTexture(GL_TEXTURE_2D, textureId);

        gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, img.getWidth(), img.getHeight(), 0, GL_BGRA,
            GL_UNSIGNED_INT_8_8_8_8_REV, img.getBuffer());

        gl.glBindTexture(GL_TEXTURE_2D, oldTextureId);
    }

    @Override
    public void loadByteArrayToTexture(GL2 gl, int textureId, int w, int h, byte[] image) {
        int oldTextureId = getCurrentTextureId(gl);

        gl.glBindTexture(GL_TEXTURE_2D, textureId);
        gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_R, w, h, 0, GL_R, GL_UNSIGNED_BYTE, Buffers.newDirectByteBuffer(image));
        gl.glBindTexture(GL_TEXTURE_2D, oldTextureId);
    }

    @Override
    public Pair<Integer, Integer> getTextureDims(GL2 gl, int textureId) {
        int oldTexture = getCurrentTextureId(gl);
        final int[] dims = new int[2];

        gl.glBindTexture(GL_TEXTURE_2D, textureId);

        gl.glGetTexParameteriv(GL_TEXTURE_WIDTH, textureId, dims, 0);
        gl.glGetTexParameteriv(GL_TEXTURE_HEIGHT, textureId, dims, 1);

        gl.glBindTexture(GL_TEXTURE_2D, oldTexture);

        return new Pair<>(dims[0], dims[1]);
    }
}
