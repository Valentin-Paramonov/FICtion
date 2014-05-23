package paramonov.valentin.fiction.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public final class IOUtils {
    public static final void writeBytesToFile(byte[] bytes, String pathString) {
        final Path path = Paths.get(pathString);
        try(final SeekableByteChannel byteChannel = Files
            .newByteChannel(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {

            final ByteBuffer buffer = ByteBuffer.wrap(bytes);
            buffer.rewind();
            byteChannel.write(buffer);
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
