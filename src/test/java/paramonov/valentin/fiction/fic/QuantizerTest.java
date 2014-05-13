package paramonov.valentin.fiction.fic;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class QuantizerTest {
    private static final int QUANTIZATION_LEVELS = 4;
    private static final int MAX_QUANTIZATION_VALUE = 1;
    private final Quantizer quantizer = new Quantizer(QUANTIZATION_LEVELS, MAX_QUANTIZATION_VALUE);

    @Test
    public void testQuantize() {
        final int quantizationLevel = quantizer.quantize(2 / 3.);

        assertThat(quantizationLevel, equalTo(3));
    }

    @Test
    public void testQuantize_ValuesAreCutOff() {
        final int quantizationLevel = quantizer.quantize(48);

        assertThat(quantizationLevel, equalTo(QUANTIZATION_LEVELS));
    }

    @Test
    public void testDeQuantize() {
        final double value = quantizer.deQuantize(3);

        assertThat(value, equalTo(0.75));
    }
}
