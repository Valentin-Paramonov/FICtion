package paramonov.valentin.fiction.io;

import org.junit.Test;

public class IOUtilsTest {
    private static final String TEST_OUTPUT_PATH = "target/test/out";

    @Test
    public void testWriteBytesToFile_SequenceWritten_IsABCD() {
        byte[] bytes = {65, 66, 67, 68};

        IOUtils.writeBytesToFile(bytes, TEST_OUTPUT_PATH + "/bytes.txt");
    }
}
