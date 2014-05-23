package paramonov.valentin.fiction.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BitUnPacker implements AutoCloseable {
    public static final int CHUNK_SIZE = 4096;
    public static final int BUFFER_BYTES = Integer.SIZE / Byte.SIZE;
    ByteBuffer fileBuffer;
    private FileChannel sourceChannel;
    private RandomAccessFile sourceFile;
    int buffer;
    private int leftInBuffer;
    private boolean sealed;

    BitUnPacker() {}

    public BitUnPacker(String fileName) throws IOException {
        attachTo(fileName);
    }

    public int read(int size) throws IOException {
        if(size < 0 || size > Integer.SIZE) {
            throw new IllegalArgumentException("Size must lie in the interval from 0 to " + Integer.SIZE);
        }

        if(leftInBuffer == 0) {
            fetchNextBuffer();
        }

        final int bufferOffset = leftInBuffer - size;

        if(bufferOffset < 0) {
            final int abs = Math.abs(leftInBuffer);
            final int mask = -1 >>> (Integer.SIZE - leftInBuffer);
            leftInBuffer = 0;
            return buffer & mask | read(abs);
        }

        int mask = -1 >>> (Integer.SIZE - size) << bufferOffset;
        leftInBuffer -= size;

        return (buffer & mask) >>> bufferOffset;
    }

    private void attachTo(String fileName) throws IOException {
        sourceFile = new RandomAccessFile(fileName, "r");
        sourceChannel = sourceFile.getChannel();
        fileBuffer = ByteBuffer.allocate(CHUNK_SIZE);
        fileBuffer.flip();
    }

    void fetchNextBuffer() throws IOException {
        if(sealed) {
            throw new EOFException("No more data.");
        }

        buffer = 0;
        for(int i = 0; i < BUFFER_BYTES; i++) {
            if(fileBuffer.position() == fileBuffer.limit()) {
                if(!fetchNextChunk()) {
                    leftInBuffer = Byte.SIZE * i;
                    seal();
                    return;
                }
            }
            if(i != 0) {
                buffer <<= Byte.SIZE;
            }
            buffer |= 0xff & fileBuffer.get();
        }
        leftInBuffer = Integer.SIZE;
    }

    private void seal() {
        sealed = true;
        fileBuffer = null;
    }

    boolean fetchNextChunk() throws IOException {
        if(sourceChannel == null) {
            return false;
        }

        fileBuffer.clear();
        final int read = sourceChannel.read(fileBuffer);
        fileBuffer.flip();
        final boolean isMoreChunksLeft = (read == CHUNK_SIZE);
        if(!isMoreChunksLeft) {
            close();
        }
        if(read == 0) {
            return false;
        }

        return true;
    }

    @Override
    public void close() throws IOException {
        if(sourceChannel != null) {
            sourceChannel.close();
            sourceChannel = null;
        }
        if(sourceFile != null) {
            sourceFile.close();
            sourceFile = null;
        }
    }

    public boolean isSealed() {
        return sealed;
    }
}
