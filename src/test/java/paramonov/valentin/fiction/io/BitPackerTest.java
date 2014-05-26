package paramonov.valentin.fiction.io;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static paramonov.valentin.fiction.TestUtils.listMatches;

public class BitPackerTest {
    private BitPacker packer;

    @Before
    public void setUp() {
        packer = new BitPacker();
    }

    @Test
    public void testPack_ArrayLength_EqualsOne() {
        packer.pack(5, 2);

        final byte[] bytes = packer.seal();

        assertThat(bytes.length, equalTo(1));
    }

    @Test
    public void testPack_ArrayLength_EqualsThree() {
        packer.pack(1024, 24);

        final byte[] bytes = packer.seal();

        assertThat(bytes.length, equalTo(3));
    }

    @Test
    public void testPack_ArrayLength_EqualsFour() {
        packer.pack(1024, 31);
        packer.pack(1024, 1);

        final byte[] bytes = packer.seal();

        assertThat(bytes.length, equalTo(4));
    }

    @Test
    public void testPack_ValueAt0_Equals64() {
        packer.pack(5, 2);

        final byte[] bytes = packer.seal();

        assertThat((int) bytes[0], equalTo(64));
    }

    @Test
    public void testPack_PackedSequence_HasExpectedValuesWhenSizeIsLessThanBuffer() {
        packer.pack(1024, 24);

        final byte[] bytes = packer.seal();
        final List<Integer> byteList = new ArrayList<>(bytes.length);
        for(byte b : bytes) {
            byteList.add((int) b);
        }

        assertThat(byteList, listMatches(0, 4, 0));
    }

    @Test
    public void testPack_PackedSequence_HasExpectedValuesWhenSizeIsGreaterThanBuffer() {
        packer.pack(1024, 18);
        packer.pack(1024, 18);

        final byte[] bytes = packer.seal();
        final List<Integer> byteList = new ArrayList<>(bytes.length);
        for(byte b : bytes) {
            byteList.add((int) b);
        }

        assertThat(byteList, listMatches(1, 0, 0, 64, 0));
    }
}
