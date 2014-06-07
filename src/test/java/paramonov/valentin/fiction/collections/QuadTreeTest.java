package paramonov.valentin.fiction.collections;

import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import paramonov.valentin.fiction.fic.FICTree;
import paramonov.valentin.fiction.fic.RangeBlock;
import paramonov.valentin.fiction.hcbc.HCBCBlock;
import paramonov.valentin.fiction.hcbc.HCBCTree;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

public class QuadTreeTest {
    private QuadTree[] children;
    private QuadTree tree;
    private FICTree ficTree;
    private HCBCTree oddTree;
    private HCBCTree evenTree;
    final RangeBlock rangeBlock = new RangeBlock(0, 0, 2, 2);

    @Before
    public void setUp() {
        tree = new HCBCTree();
        children = new QuadTree[4];
        ficTree = new FICTree();
        ficTree.add(rangeBlock);
        oddTree = new HCBCTree();
        oddTree.add(new HCBCBlock(0, 0, 5, 5, 0, 0, 0));
        evenTree = new HCBCTree();
        evenTree.add(new HCBCBlock(0, 0, 6, 6, 0, 0, 0));
    }

    @Test
    public void testHasChildren_EmptyArray() {
        boolean hasChildren = tree.hasChildren();

        assertFalse(hasChildren);
    }

    @Test
    public void testNoChildren_NonEmptyArray() {
        tree.children = children;
        children[0] = new HCBCTree();

        boolean hasChildren = tree.hasChildren();

        assertTrue(hasChildren);
    }

    @Test
    public void testFindPlace_ShouldBe0() throws Exception {
        final RangeBlock rangeBlock = new RangeBlock(0, 0, 1, 1);

        final int place = ficTree.findPlace(rangeBlock);

        assertThat(place, equalTo(0));
    }

    @Test
    public void testFindPlace_ShouldBe1() throws Exception {
        final RangeBlock rangeBlock = new RangeBlock(1, 0, 1, 1);

        final int place = ficTree.findPlace(rangeBlock);

        assertThat(place, equalTo(1));
    }

    @Test
    public void testFindPlace_ShouldBe2() throws Exception {
        final RangeBlock rangeBlock = new RangeBlock(0, 1, 1, 1);

        final int place = ficTree.findPlace(rangeBlock);

        assertThat(place, equalTo(2));
    }

    @Test
    public void testFindPlace_ShouldBe3() throws Exception {
        final RangeBlock rangeBlock = new RangeBlock(1, 1, 1, 1);

        final int place = ficTree.findPlace(rangeBlock);

        assertThat(place, equalTo(3));
    }

    @Test
    public void testFindPlace_BelongsToFirstQuad() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0, 0);

        int quad = oddTree.findPlace(block);

        MatcherAssert.assertThat(quad, equalTo(0));
    }

    @Test
    public void testFindPlace_BelongsToFirstQuadInSquareTree() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0, 0);

        int quad = evenTree.findPlace(block);

        MatcherAssert.assertThat(quad, equalTo(0));
    }

    @Test
    public void testFindPlace_BelongsToSecondQuad() {
        HCBCBlock block = new HCBCBlock(3, 0, 2, 3, 0, 0, 0);

        int quad = oddTree.findPlace(block);

        MatcherAssert.assertThat(quad, equalTo(1));
    }

    @Test
    public void testFindPlace_BelongsToSecondQuadInSquareTree() {
        HCBCBlock block = new HCBCBlock(3, 0, 3, 3, 0, 0, 0);

        int quad = evenTree.findPlace(block);

        MatcherAssert.assertThat(quad, equalTo(1));
    }

    @Test
    public void testFindPlace_BelongsToThirdQuad() {
        HCBCBlock block = new HCBCBlock(0, 3, 3, 2, 0, 0, 0);

        int quad = oddTree.findPlace(block);

        MatcherAssert.assertThat(quad, equalTo(2));
    }

    @Test
    public void testFindPlace_BelongsToThirdQuadInSquareTree() {
        HCBCBlock block = new HCBCBlock(0, 3, 3, 3, 0, 0, 0);

        int quad = evenTree.findPlace(block);

        MatcherAssert.assertThat(quad, equalTo(2));
    }

    @Test
    public void testFindPlace_BelongsToFourthQuad() {
        HCBCBlock block = new HCBCBlock(3, 3, 2, 2, 0, 0, 0);

        int quad = oddTree.findPlace(block);

        MatcherAssert.assertThat(quad, equalTo(3));
    }

    @Test
    public void testFindPlace_BelongsToFourthQuadInSquareTree() {
        HCBCBlock block = new HCBCBlock(3, 3, 3, 3, 0, 0, 0);

        int quad = evenTree.findPlace(block);

        MatcherAssert.assertThat(quad, equalTo(3));
    }

    @Test(expected = BlockDimensionsException.class)
    public void testFindPlace_WiderThanHalf_ThrowsException() {
        HCBCBlock block = new HCBCBlock(3, 3, 4, 3, 0, 0, 0);

        int quad = oddTree.findPlace(block);
    }

    @Test
    public void testAdd_AddsValidBlock() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0, 0);

        boolean added = evenTree.add(block);

        assertTrue(added);
    }

    @Test(expected = BlockDimensionsException.class)
    public void testAdd_DoesNotAddInvalidBlock() {
        HCBCBlock block = new HCBCBlock(0, 0, 7, 7, 0, 0, 0);

        boolean added = evenTree.add(block);
    }

    @Test
    public void testAdd_DoesNotAddSameBlockTwice() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0, 0);

        evenTree.add(block);
        boolean added = evenTree.add(block);

        assertFalse(added);
    }

    @Test
    public void testSize_NewTree() {
        HCBCTree tree = new HCBCTree();

        int size = tree.size();

        Assert.assertThat(size, equalTo(0));
    }

    @Test
    public void testSize_UpdatesOnAdd() {
        HCBCBlock block1 = new HCBCBlock(0, 0, 3, 3, 0, 0, 0);
        HCBCBlock block2 = new HCBCBlock(3, 3, 3, 3, 0, 0, 0);

        evenTree.add(block1);
        evenTree.add(block2);

        int size = evenTree.size();

        Assert.assertThat(size, equalTo(3));
    }

    @Test
    public void testSize_Expected() {
        HCBCTree tree = new HCBCTree();

        tree.add(new HCBCBlock(0, 0, 2, 2, 0, 0, 0));
        tree.add(new HCBCBlock(0, 0, 1, 1, 1, 1, 1));
        tree.add(new HCBCBlock(1, 0, 1, 1, 2, 2, 2));
        tree.add(new HCBCBlock(0, 1, 1, 1, 3, 3, 3));
        tree.add(new HCBCBlock(1, 1, 1, 1, 4, 4, 4));

        int treeSize = tree.size();

        Assert.assertThat(treeSize, equalTo(5));
    }
}
