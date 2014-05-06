package paramonov.valentin.fiction.gl.processor;

import javax.media.opengl.GL2;

public interface GLImageProcessor {
    void drawTexture(GL2 gl, int framebufferId, int textureId);
}
