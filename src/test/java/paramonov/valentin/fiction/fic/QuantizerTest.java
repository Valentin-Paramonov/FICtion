package paramonov.valentin.fiction.fic;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class QuantizerTest {
    private static final int QUANTIZATION_LEVELS = 4;
    private static final int MIN_QUANTIZATION_VALUE = 0;
    private static final int MAX_QUANTIZATION_VALUE = 1;
    private final Quantizer quantizer =
        new Quantizer(QUANTIZATION_LEVELS, MIN_QUANTIZATION_VALUE, MAX_QUANTIZATION_VALUE);

    @Test
    public void testQuantize() {
        final int quantizationLevel = quantizer.quantize(2/3.);

        assertThat(quantizationLevel, equalTo(3));
    }

    @Test
    public void testQuantize_ValuesAreCutOff() {
        final int quantizationLevel = quantizer.quantize(48);

        assertThat(quantizationLevel, equalTo(QUANTIZATION_LEVELS - 1));
    }

    @Test
    public void testDeQuantize_WhenLevel3() {
        final double value = quantizer.deQuantize(3);

        assertThat(value, equalTo(0.75));
    }

    @Test
    public void testName() {
        final int quantize = new Quantizer(32, -1, 1).quantize(.5);

        assertThat(quantize, equalTo(8));
    }
}
