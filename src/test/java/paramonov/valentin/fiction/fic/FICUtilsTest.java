package paramonov.valentin.fiction.fic;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.Resources;
import paramonov.valentin.fiction.image.Image;
import paramonov.valentin.fiction.transformation.Transformation;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FICUtilsTest {
    private static final int[] COLORS = {1, 2, 3, 4};
    private static final Image TEST_IMAGE = new Image(COLORS, 2, 2);

    @Test
    public void testComputeDifferencesRms_SameImageRMSIsZero() {
        Image transformationImage = new Image(COLORS, 2, 2);

        final List<TransformationParams> parameters = FICUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final double rmsIdentity = parameters.get(Transformation.IDENTITY.ordinal()).getRms();

        assertThat(rmsIdentity, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_DifferentImageRMSHasExpectedValue() {
        final int[] colors = {2, 1, 4, 3};
        Image transformationImage = new Image(colors, 2, 2);

        final List<TransformationParams> parameters = FICUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final double rmsIdentity = parameters.get(Transformation.IDENTITY.ordinal()).getRms();
        final BigDecimal result = new BigDecimal(rmsIdentity).setScale(10, BigDecimal.ROUND_HALF_EVEN);
        final BigDecimal expectedResult = new BigDecimal(.8).setScale(10, BigDecimal.ROUND_HALF_EVEN);

        assertThat(result, equalTo(expectedResult));
    }

    @Test
    public void testComputeDifferencesRms_ReflectedHorizontally() {
        final int[] colors = {3, 4, 1, 2};
        Image transformationImage = new Image(colors, 2, 2);

        final List<TransformationParams> parameters = FICUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final double rms = parameters.get(Transformation.REFLECTION_HORIZONTAL.ordinal()).getRms();

        assertThat(rms, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_ReflectedVertically() {
        final int[] colors = {2, 1, 4, 3};
        Image transformationImage = new Image(colors, 2, 2);

        final List<TransformationParams> parameters = FICUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final double rms = parameters.get(Transformation.REFLECTION_VERTICAL.ordinal()).getRms();

        assertThat(rms, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_ReflectedDiagonally() {
        final int[] colors = {1, 3, 2, 4};
        Image transformationImage = new Image(colors, 2, 2);

        final List<TransformationParams> parameters = FICUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final double rms = parameters.get(Transformation.REFLECTION_DIAGONAL.ordinal()).getRms();

        assertThat(rms, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_ReflectedDiagonallyNegative() {
        final int[] colors = {4, 2, 3, 1};
        Image transformationImage = new Image(colors, 2, 2);

        final List<TransformationParams> parameters = FICUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final double rms = parameters.get(Transformation.REFLECTION_NEGATIVE_DIAGONAL.ordinal()).getRms();

        assertThat(rms, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_Rotated90CW() {
        final int[] colors = {3, 1, 4, 2};
        Image transformationImage = new Image(colors, 2, 2);

        final List<TransformationParams> parameters = FICUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final double rms = parameters.get(Transformation.ROTATION_90CW.ordinal()).getRms();

        assertThat(rms, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_Rotated90CCW() {
        final int[] colors = {2, 4, 1, 3};
        Image transformationImage = new Image(colors, 2, 2);

        final List<TransformationParams> parameters = FICUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final double rms = parameters.get(Transformation.ROTATION_90CCW.ordinal()).getRms();

        assertThat(rms, equalTo(0.));
    }

    @Test
    public void testComputeDifferencesRms_Rotated180() {
        final int[] colors = {4, 3, 2, 1};
        Image transformationImage = new Image(colors, 2, 2);

        final List<TransformationParams> parameters = FICUtils.computeDifferencesRms(TEST_IMAGE, transformationImage);
        final double rms = parameters.get(Transformation.ROTATION_180.ordinal()).getRms();

        assertThat(rms, equalTo(0.));
    }
}
