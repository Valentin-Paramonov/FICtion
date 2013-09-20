package paramonov.valentin.fiction.collections.hcbc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import paramonov.valentin.fiction.collections.QuadTree;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class HCBCTreeTest {
    private HCBCTree tree = Mockito.spy(new HCBCTree(5, 5));
    private HCBCTree squareTree = Mockito.spy(new HCBCTree(6, 6));

    private QuadTree[] children = new QuadTree[4];

    @Before
    public void setUp() throws Exception {
        doReturn(children).when(tree).getChildren();
    }

    @Test
    public void testNoChildren_EmptyArray() {
        boolean noChildren = tree.noChildren();

        assertThat(noChildren, equalTo(true));
    }

    @Test
    public void testNoChildren_NonEmptyArray() {
        children[0] = tree;

        boolean noChildren = tree.noChildren();

        assertThat(noChildren, equalTo(false));
    }

    @Test
    public void testFits_Belongs() {
        boolean belongs = tree.fits(1, 5, 2);

        assertTrue(belongs);
    }

    @Test
    public void testFits_LeftFromRegion() {
        boolean belongs = tree.fits(1, 5, 0);

        assertFalse(belongs);
    }

    @Test
    public void testFits_RightFromRegion() {
        boolean belongs = tree.fits(1, 5, 7);

        assertFalse(belongs);
    }

    @Test
    public void testFits_LeftBoundary() {
        boolean belongs = tree.fits(1, 5, 1);

        assertTrue(belongs);
    }

    @Test
    public void testFits_RightBoundary() {
        boolean belongs = tree.fits(1, 5, 5);

        assertFalse(belongs);
    }

    @Test
    public void testFindPlace_BelongsToFirstQuad() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0, 0);

        int quad = tree.findPlace(block);

        assertThat(quad, equalTo(0));
    }

    @Test
    public void testFindPlace_BelongsToFirstQuadInSquareTree() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0, 0);

        int quad = squareTree.findPlace(block);

        assertThat(quad, equalTo(0));
    }

    @Test
    public void testFindPlace_BelongsToSecondQuad() {
        HCBCBlock block = new HCBCBlock(3, 0, 2, 3, 0, 0, 0);

        int quad = tree.findPlace(block);

        assertThat(quad, equalTo(1));
    }

    @Test
    public void testFindPlace_BelongsToSecondQuadInSquareTree() {
        HCBCBlock block = new HCBCBlock(3, 0, 3, 3, 0, 0, 0);

        int quad = squareTree.findPlace(block);

        assertThat(quad, equalTo(1));
    }

    @Test
    public void testFindPlace_BelongsToThirdQuad() {
        HCBCBlock block = new HCBCBlock(0, 3, 3, 2, 0, 0, 0);

        int quad = tree.findPlace(block);

        assertThat(quad, equalTo(2));
    }

    @Test
    public void testFindPlace_BelongsToThirdQuadInSquareTree() {
        HCBCBlock block = new HCBCBlock(0, 3, 3, 3, 0, 0, 0);

        int quad = squareTree.findPlace(block);

        assertThat(quad, equalTo(2));
    }

    @Test
    public void testFindPlace_BelongsToFourthQuad() {
        HCBCBlock block = new HCBCBlock(3, 3, 2, 2, 0, 0, 0);

        int quad = tree.findPlace(block);

        assertThat(quad, equalTo(3));
    }

    @Test
    public void testFindPlace_BelongsToFourthQuadInSquareTree() {
        HCBCBlock block = new HCBCBlock(3, 3, 3, 3, 0, 0, 0);

        int quad = squareTree.findPlace(block);

        assertThat(quad, equalTo(3));
    }

    @Test
    public void testFindPlace_WiderThanHalf() {
        HCBCBlock block = new HCBCBlock(3, 3, 4, 3, 0, 0, 0);

        int quad = tree.findPlace(block);

        assertThat(quad, equalTo(-1));
    }

    @Test
    public void testAdd_AddsValidBlock() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0 ,0);

        boolean added = squareTree.add(block);

        assertTrue(added);
    }

    @Test
    public void testAdd_DoesNotAddInvalidBlock() {
        HCBCBlock block = new HCBCBlock(0, 0, 7, 7, 0, 0 ,0);

        boolean added = squareTree.add(block);

        assertFalse(added);
    }

    @Test
    public void testAdd_DoesNotAddSameBlockTwice() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0 ,0);

        squareTree.add(block);
        boolean added = squareTree.add(block);

        assertFalse(added);
    }

    @Test
    public void testSize_NewTree() {
        int size = squareTree.size();

        Assert.assertThat(size, equalTo(1));
    }

    @Test
    public void testSize_UpdatesOnAdd() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0 ,0);

        squareTree.add(block);

        int size = squareTree.size();

        Assert.assertThat(size, equalTo(2));
    }
}
