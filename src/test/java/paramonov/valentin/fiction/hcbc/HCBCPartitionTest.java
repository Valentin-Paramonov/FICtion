package paramonov.valentin.fiction.hcbc;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HCBCPartitionTest {
    private HCBCPartition partition;
    private double[][][] tc = {
        {{0, 0, 0}, {1., 2., 3.}},
        {{4., 5., 6.}, {7., 8., 9.}}};

    @Test
    public void testCalculateMeanTC_ProducesExpectedValues_WholeBlockStartAtZero() {
        partition = new HCBCPartition(null, tc, 0, 0, 0, 0, 0, 2, 2);

        double[] meanValues = partition.calculateMeanTC();

        double meanTCB = meanValues[1];

        assertThat(meanTCB, equalTo(3.75));
    }

    @Test
    public void testCalculateMeanTC_ProducesExpectedValues_SubBlockShifted() {
        partition = new HCBCPartition(null, tc, 0, 0, 0, 1, 1, 1, 1);

        double[] meanValues = partition.calculateMeanTC();

        double meanTCB = meanValues[1];

        assertThat(meanTCB, equalTo(8.));
    }

    @Test
    public void testCalculateVariance_ProducesExpectedValues_WholeBlockStartAtZero() {
        partition =
            new HCBCPartition(
                null, tc, 0, 0, 0, 0, 0, 2, 2);

        double[] meanValues = partition.calculateMeanTC();
        double[] variance = partition.calculateVariance(meanValues);

        double varB = variance[1];

        assertThat(varB, equalTo(9.1875));
    }

    @Test
    public void testCalculateVariance_ProducesExpectedValues_SubBlockShifted() {
        partition = new HCBCPartition(null, tc, 0, 0, 0, 1, 1, 1, 1);

        double[] meanValues = partition.calculateMeanTC();
        double[] variance = partition.calculateVariance(meanValues);

        double varB = variance[1];

        assertThat(varB, equalTo(0.));
    }
}
