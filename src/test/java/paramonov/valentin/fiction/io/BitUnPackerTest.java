package paramonov.valentin.fiction.io;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.ByteBuffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;
import static paramonov.valentin.fiction.ListMatcher.listMatches;

@RunWith(MockitoJUnitRunner.class)
public class BitUnPackerTest {
    private static final String OUTPUT_RESOURCE_PATH = "target/test/out";

    @Spy
    private BitUnPacker unpacker = new BitUnPacker();

    @Before
    public void setUp() throws Exception {
        doReturn(false).when(unpacker).fetchNextChunk();
        final ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putInt(0xff).putInt(0x7f).putInt(0x0f).put((byte) 0x77);
        byteBuffer.flip();
        unpacker.fileBuffer = byteBuffer;
    }

    @Test
    public void testByteBuffer_Position_Equals0() {
        final int position = unpacker.fileBuffer.position();

        assertThat(position, equalTo(0));
    }

    @Test
    public void testByteBuffer_Limit_Equals13() {
        final int limit = unpacker.fileBuffer.limit();

        assertThat(limit, equalTo(13));
    }

    @Test
    public void testFetchNextBuffer_BufferValue_Equals0() throws Exception {
        unpacker.fetchNextBuffer();

        assertThat(unpacker.buffer, equalTo(0xff));
    }

    @Test
    public void testRead_First5Bits_Equal0() throws Exception {
        final int read = unpacker.read(5);

        assertThat(read, equalTo(0));
    }

    @Test
    public void testRead_FirstInt_Equals0xff() throws Exception {
        final int read = unpacker.read(32);

        assertThat(read, equalTo(0xff));
    }

    @Test
    public void testRead_FirstByteInSecondChunk_Equals7() throws Exception {
        final BitUnPacker unPacker = new BitUnPacker("src/test/resources/bytes.dat");
        final int bytesInInteger = Integer.SIZE / Byte.SIZE;
        final int numOfIntegersInChunk = BitUnPacker.CHUNK_SIZE / bytesInInteger;
        for(int i = 0; i < numOfIntegersInChunk; i++) {
            unPacker.read(Integer.SIZE);
        }

        final int read = unPacker.read(8);

        assertThat(read, equalTo(7));
    }

    @Test
    public void testRead_PackedBits_ReadValueIsTheSameAsStored() throws Exception {
        final BitPacker bitPacker = new BitPacker().pack(0xff, 8).pack(0, 3).pack(0x07, 3);
        final byte[] bytes = bitPacker.seal();
        final String filePath = OUTPUT_RESOURCE_PATH + "/packed.fic";
        Files.write(FileSystems.getDefault().getPath(filePath), bytes,
            StandardOpenOption.CREATE);
        final BitUnPacker bitUnPacker = new BitUnPacker(filePath);

        final List<Integer> integers = Arrays.asList(bitUnPacker.read(8), bitUnPacker.read(3), bitUnPacker.read(3));

        assertThat(integers, listMatches(0xff,0,0x07));
    }
}
