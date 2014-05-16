package paramonov.valentin.fiction.io;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
    public void testPack_Values_EqualTo0Then4Then0() {
        packer.pack(1024, 24);

        final byte[] bytes = packer.seal();

        assertThat((int) bytes[0], equalTo(0));
        assertThat((int) bytes[1], equalTo(4));
        assertThat((int) bytes[2], equalTo(0));
    }

    @Test
    public void testPack_Values_EqualTo1Then0Then0Then64Then0() {
        packer.pack(1024, 18);
        packer.pack(1024, 18);

        final byte[] bytes = packer.seal();

        assertThat((int) bytes[0], equalTo(1));
        assertThat((int) bytes[1], equalTo(0));
        assertThat((int) bytes[2], equalTo(0));
        assertThat((int) bytes[3], equalTo(64));
        assertThat((int) bytes[4], equalTo(0));
    }
}
