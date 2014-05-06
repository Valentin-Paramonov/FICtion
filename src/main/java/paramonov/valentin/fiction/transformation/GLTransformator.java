package paramonov.valentin.fiction.transformation;

import javax.media.opengl.GL2;

public interface GLTransformator {
    byte[] transform(GL2 gl, int textureId);
}
