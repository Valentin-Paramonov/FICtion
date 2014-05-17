package paramonov.valentin.fiction.collections;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.hcbc.HCBCBlock;
import paramonov.valentin.fiction.hcbc.HCBCTree;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class QuadTreeIteratorTest {
    private HCBCTree squareTree;
    private HCBCTree rectTree;

    @Before
    public void setUp() throws Exception {
        squareTree = new HCBCTree();
        squareTree.add(new HCBCBlock(0, 0, 2, 2, 0, 0, 0));
        squareTree.add(new HCBCBlock(0, 0, 1, 1, 1, 1, 1));
        squareTree.add(new HCBCBlock(1, 0, 1, 1, 2, 2, 2));
        squareTree.add(new HCBCBlock(0, 1, 1, 1, 3, 3, 3));
        squareTree.add(new HCBCBlock(1, 1, 1, 1, 4, 4, 4));

        rectTree = new HCBCTree();
        rectTree.add(new HCBCBlock(0, 0, 3, 3, 0, 0, 0));
        rectTree.add(new HCBCBlock(0, 0, 2, 2, 1, 1, 1));
        rectTree.add(new HCBCBlock(2, 0, 1, 2, 2, 2, 2));
        rectTree.add(new HCBCBlock(0, 2, 2, 1, 3, 3, 3));
        rectTree.add(new HCBCBlock(2, 2, 1, 1, 4, 4, 4));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        Iterator<HCBCBlock> iterator = squareTree.iterator();

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
        Iterator<HCBCBlock> iterator = squareTree.iterator();

        boolean hasNext = iterator.hasNext();

        assertTrue(hasNext);
    }

    @Test
    public void testSize_SquareTree_Equals4() {
        final int squareTreeSize = squareTree.size();

        assertThat(squareTreeSize, equalTo(4));
    }

    @Test
    public void testSize_RectTree_Equals4() {
        final int rectTreeSize = rectTree.size();

        assertThat(rectTreeSize, equalTo(4));
    }

    @Test
    public void testNext_SquareTree_SequenceInRightOrder() {
        double value = 0;
        ;
        for(HCBCBlock block : squareTree) {
            assertThat(block.getMtcR(), equalTo(value));
            value++;
        }
    }

    @Test
    public void testNext_SequenceInRightOrder_RectTree() {
        double value = 0;

        for(HCBCBlock block : rectTree) {
            assertThat(block.getMtcR(), equalTo(value));
            value++;
        }
    }
}
