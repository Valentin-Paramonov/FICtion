package paramonov.valentin.fiction.gui.gl.processor;

import paramonov.valentin.fiction.image.Image;

import javax.media.opengl.GL2;

public interface GLTextureProcessor {
    int createTexture(GL2 gl);
    int createTextureFromImage(GL2 gl, Image img);
    int createTextureFromByteArray(GL2 gl, int w, int h, byte[] image);
    int getCurrentTextureId(GL2 gl);
    int genTextureId(GL2 gl);
    void loadImageToTexture(GL2 gl, int textureId, Image img);
    void loadByteArrayToTexture(GL2 gl, int textureId, int w, int h, byte[] image);
}
