package paramonov.valentin.fiction.fic;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FICTreeTest {
    final RangeBlock rangeBlock = new RangeBlock(0, 0, 2, 2);
    private FICTree tree;

    @Before
    public void setUp() throws Exception {
        tree = new FICTree();
        tree.add(rangeBlock);
    }

    @Test
    public void testFindPlace_ShouldBe0() throws Exception {
        final RangeBlock rangeBlock = new RangeBlock(0, 0, 1, 1);

        final int place = tree.findPlace(rangeBlock);

        assertThat(place, equalTo(0));
    }

    @Test
    public void testFindPlace_ShouldBe1() throws Exception {
        final RangeBlock rangeBlock = new RangeBlock(1, 0, 1, 1);

        final int place = tree.findPlace(rangeBlock);

        assertThat(place, equalTo(1));
    }

    @Test
    public void testFindPlace_ShouldBe2() throws Exception {
        final RangeBlock rangeBlock = new RangeBlock(0, 1, 1, 1);

        final int place = tree.findPlace(rangeBlock);

        assertThat(place, equalTo(2));
    }

    @Test
    public void testFindPlace_ShouldBe3() throws Exception {
        final RangeBlock rangeBlock = new RangeBlock(1, 1, 1, 1);

        final int place = tree.findPlace(rangeBlock);

        assertThat(place, equalTo(3));
    }
}
