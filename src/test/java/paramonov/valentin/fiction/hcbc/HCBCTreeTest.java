package paramonov.valentin.fiction.hcbc;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HCBCTreeTest {
    private HCBCTree oddTree = new HCBCTree(5, 5);
    private HCBCTree evenTree = new HCBCTree(6, 6);

    @Test
    public void testFits_Belongs() {
        boolean belongs = oddTree.fits(1, 5, 2);

        assertTrue(belongs);
    }

    @Test
    public void testFits_LeftFromRegion() {
        boolean belongs = oddTree.fits(1, 5, 0);

        assertFalse(belongs);
    }

    @Test
    public void testFits_RightFromRegion() {
        boolean belongs = oddTree.fits(1, 5, 7);

        assertFalse(belongs);
    }

    @Test
    public void testFits_LeftBoundary() {
        boolean belongs = oddTree.fits(1, 5, 1);

        assertTrue(belongs);
    }

    @Test
    public void testFits_RightBoundary() {
        boolean belongs = oddTree.fits(1, 5, 5);

        assertFalse(belongs);
    }

    @Test
    public void testFindPlace_BelongsToFirstQuad() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0, 0);

        int quad = oddTree.findPlace(block);

        assertThat(quad, equalTo(0));
    }

    @Test
    public void testFindPlace_BelongsToFirstQuadInSquareTree() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0, 0);

        int quad = evenTree.findPlace(block);

        assertThat(quad, equalTo(0));
    }

    @Test
    public void testFindPlace_BelongsToSecondQuad() {
        HCBCBlock block = new HCBCBlock(3, 0, 2, 3, 0, 0, 0);

        int quad = oddTree.findPlace(block);

        assertThat(quad, equalTo(1));
    }

    @Test
    public void testFindPlace_BelongsToSecondQuadInSquareTree() {
        HCBCBlock block = new HCBCBlock(3, 0, 3, 3, 0, 0, 0);

        int quad = evenTree.findPlace(block);

        assertThat(quad, equalTo(1));
    }

    @Test
    public void testFindPlace_BelongsToThirdQuad() {
        HCBCBlock block = new HCBCBlock(0, 3, 3, 2, 0, 0, 0);

        int quad = oddTree.findPlace(block);

        assertThat(quad, equalTo(2));
    }

    @Test
    public void testFindPlace_BelongsToThirdQuadInSquareTree() {
        HCBCBlock block = new HCBCBlock(0, 3, 3, 3, 0, 0, 0);

        int quad = evenTree.findPlace(block);

        assertThat(quad, equalTo(2));
    }

    @Test
    public void testFindPlace_BelongsToFourthQuad() {
        HCBCBlock block = new HCBCBlock(3, 3, 2, 2, 0, 0, 0);

        int quad = oddTree.findPlace(block);

        assertThat(quad, equalTo(3));
    }

    @Test
    public void testFindPlace_BelongsToFourthQuadInSquareTree() {
        HCBCBlock block = new HCBCBlock(3, 3, 3, 3, 0, 0, 0);

        int quad = evenTree.findPlace(block);

        assertThat(quad, equalTo(3));
    }

    @Test
    public void testFindPlace_WiderThanHalf() {
        HCBCBlock block = new HCBCBlock(3, 3, 4, 3, 0, 0, 0);

        int quad = oddTree.findPlace(block);

        assertThat(quad, equalTo(-1));
    }

    @Test
    public void testAdd_AddsValidBlock() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0 ,0);

        boolean added = evenTree.add(block);

        assertTrue(added);
    }

    @Test
    public void testAdd_DoesNotAddInvalidBlock() {
        HCBCBlock block = new HCBCBlock(0, 0, 7, 7, 0, 0 ,0);

        boolean added = evenTree.add(block);

        assertFalse(added);
    }

    @Test
    public void testAdd_DoesNotAddSameBlockTwice() {
        HCBCBlock block = new HCBCBlock(0, 0, 3, 3, 0, 0 ,0);

        evenTree.add(block);
        boolean added = evenTree.add(block);

        assertFalse(added);
    }

    @Test
    public void testSize_NewTree() {
        int size = evenTree.size();

        Assert.assertThat(size, equalTo(0));
    }

    @Test
    public void testSize_UpdatesOnAdd() {
        HCBCBlock block1 = new HCBCBlock(0, 0, 3, 3, 0, 0 ,0);
        HCBCBlock block2 = new HCBCBlock(3, 3, 3, 3, 0, 0 ,0);

        evenTree.add(block1);
        evenTree.add(block2);

        int size = evenTree.size();

        Assert.assertThat(size, equalTo(2));
    }
}
