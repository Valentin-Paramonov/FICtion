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

@RunWith(MockitoJUnitRunner.class)
public class QuadTreeTest {
    private QuadTree[] children = new QuadTree[4];

    @Spy
    private QuadTree tree = (QuadTree) new HCBCTree();

    @Before
    public void setUp() {
        doReturn(children).when(tree).getChildren();
    }

    @Test
    public void testHasChildren_EmptyArray() {
        boolean hasChildren = tree.hasChildren();

        assertFalse(hasChildren);
    }

    @Test
    public void testNoChildren_NonEmptyArray() {
        children[0] = tree;

        boolean hasChildren = tree.hasChildren();

        assertTrue(hasChildren);
    }
}
