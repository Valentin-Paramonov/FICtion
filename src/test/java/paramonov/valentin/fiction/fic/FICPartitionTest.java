package paramonov.valentin.fiction.fic;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class FICPartitionTest {
    @Test
    public void testFindMinimalRms_TransformationParameter_ShouldBeMinimal() {
        final FICPartition partition = new FICPartition(null, null, null, 0, 0, 0, 0, 0);
        final TransformationParams params1 = new TransformationParams(null, 7, 0, 0);
        final TransformationParams params2 = new TransformationParams(null, -9, 0, 0);
        final TransformationParams params3 = new TransformationParams(null, 3, 0, 0);
        final TransformationParams params4 = new TransformationParams(null, -9, 0, 0);
        final TransformationParams params5 = new TransformationParams(null, 0, 0, 0);
        final List<TransformationParams> params = Arrays.asList(params1, params2, params3, params4, params5);

        final TransformationParams minimalRms = partition.findMinimalRms(params);

        assertThat(minimalRms, equalTo(params2));
    }
}
