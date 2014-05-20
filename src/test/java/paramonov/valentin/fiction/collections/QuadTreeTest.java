package paramonov.valentin.fiction.collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import paramonov.valentin.fiction.hcbc.HCBCTree;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

public class QuadTreeTest {
    private QuadTree[] children;
    private QuadTree tree;

    @Before
    public void setUp() {
        tree = new HCBCTree();
        children = new QuadTree[4];
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
}
