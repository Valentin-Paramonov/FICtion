package paramonov.valentin.fiction.hcbc;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HCBCIteratorTest {
    private HCBCTree tree;

    @Before
    public void setUp() throws Exception {
        tree = new HCBCTree();
        tree.add(new HCBCBlock(0, 0, 2, 2, 0, 0, 0));
        tree.add(new HCBCBlock(0, 0, 1, 1, 1, 1, 1));
        tree.add(new HCBCBlock(1, 0, 1, 1, 2, 2, 2));
        tree.add(new HCBCBlock(0, 1, 1, 1, 3, 3, 3));
        tree.add(new HCBCBlock(1, 1, 1, 1, 4, 4, 4));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        Iterator<HCBCBlock> iterator = tree.iterator();

        iterator.remove();
    }

    @Test
    public void testHasNext_EmptyTree() {
        HCBCTree tree = new HCBCTree();
        Iterator<HCBCBlock> iterator = tree.iterator();

        boolean hasNext = iterator.hasNext();

        assertFalse(hasNext);
    }

    @Test
    public void testHasNext_NonEmptyTree() {
        Iterator<HCBCBlock> iterator = tree.iterator();

        boolean hasNext = iterator.hasNext();

        assertTrue(hasNext);
    }

    @Test
    public void testNext_SequenceInRightOrder() {
        double value = 1;

        for(HCBCBlock block : tree) {
            assertThat(block.getMtcR(), equalTo(value));
            value++;
        }
    }
}
