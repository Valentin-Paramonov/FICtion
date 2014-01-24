package paramonov.valentin.fiction.transformation;

import com.jogamp.common.nio.Buffers;
import paramonov.valentin.fiction.gui.gl.processor.GLFramebufferProcessor;
import paramonov.valentin.fiction.gui.gl.processor.GLImageProcessor;
import paramonov.valentin.fiction.gui.gl.processor.GLTextureProcessor;
import paramonov.valentin.fiction.gui.gl.processor.InvalidBufferSizeException;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.awt.GLCanvas;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class TransformationGLCanvas extends GLCanvas implements GLEventListener {
    private enum Operation {
        ROTATE_90_CW,
        IDLE
    }

    private final int width;
    private final int height;
    private final GLFramebufferProcessor framebufferProcessor;
    private final GLTextureProcessor textureProcessor;
    private final GLImageProcessor imageProcessor;
    private ByteBuffer backingBuffer;

    private Operation operation = Operation.IDLE;
    private int framebufferId;

    private int imageWidth;
    private int imageHeight;
    private byte[] image;

    public TransformationGLCanvas(int width, int height, GLFramebufferProcessor framebufferProcessor,
        GLTextureProcessor textureProcessor, GLImageProcessor imageProcessor) throws GLException {

        this.width = width;
        this.height = height;
        this.framebufferProcessor = framebufferProcessor;
        this.textureProcessor = textureProcessor;
        this.imageProcessor = imageProcessor;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        backingBuffer = Buffers.newDirectByteBuffer(width * height);

        try {
            framebufferId = framebufferProcessor.createGreyFramebuffer(gl, width, height, backingBuffer);
        } catch(InvalidBufferSizeException ibse) {
            ibse.printStackTrace();
        }
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();

        switch(operation) {
        }

        operation = Operation.IDLE;
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i2, int i3, int i4) {
        return;
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        return;
    }

    private byte[] performTransformation(Operation transformation, int w, int h, byte[] image) {
        imageWidth = w;
        imageHeight = h;
        this.image = image;

        operation = transformation;
        display();

        imageWidth = -1;
        imageHeight = -1;
        this.image = null;

        byte[] img = backingBuffer.array();
        return Arrays.copyOf(img, img.length);
    }

    public byte[] rotate90CW(int w, int h, byte[] image) {
       return performTransformation(Operation.ROTATE_90_CW, w, h, image);
    }
}
