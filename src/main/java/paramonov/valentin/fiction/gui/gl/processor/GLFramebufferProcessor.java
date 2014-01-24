package paramonov.valentin.fiction.gui.gl.processor;

import javax.media.opengl.GL2;
import java.nio.ByteBuffer;

public interface GLFramebufferProcessor {
    int createRGBAFramebuffer(GL2 gl, int w, int h);
    int createRGBAFramebuffer(GL2 gl, int w, int h, ByteBuffer buffer) throws InvalidBufferSizeException;
    int createGreyFramebuffer(GL2 gl, int w, int h);
    int createGreyFramebuffer(GL2 gl, int w, int h, ByteBuffer buffer) throws InvalidBufferSizeException;
    int getCurrentFramebufferId(GL2 gl);
    int genFramebufferId(GL2 gl);
}
