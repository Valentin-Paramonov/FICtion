package paramonov.valentin.fiction.io;

import java.io.ByteArrayOutputStream;

public class BitPacker {
    private ByteArrayOutputStream outputStream;
    private byte buffer;
    private int leftInBuffer;
    private boolean sealed = true;

    public BitPacker() {}

    private void init() {
        outputStream = new ByteArrayOutputStream();
        sealed = false;
        reset();
    }

    void pack(int data, int size) {
        if(sealed) {
            init();
        }

        data &= (1 << size) - 1;
        final int bufferSpace = leftInBuffer - size;
        if(bufferSpace <= 0) {
            final int abs = Math.abs(bufferSpace);
            buffer |= data >>> abs;
            outputStream.write(buffer);
            reset();

            pack(data, abs);
        } else {
            buffer |= data << bufferSpace;
            leftInBuffer -= size;
        }
    }

    public byte[] seal() {
        sealed = true;
        if(bufferIsNotEmpty()) {
            outputStream.write(buffer);
        }

        return outputStream.toByteArray();
    }

    private boolean bufferIsNotEmpty() {
        return leftInBuffer != Byte.SIZE;
    }

    private void reset() {
        buffer = 0;
        leftInBuffer = Byte.SIZE;
    }
}
