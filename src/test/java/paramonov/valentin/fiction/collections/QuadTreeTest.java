package paramonov.valentin.fiction.collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import paramonov.valentin.fiction.hcbc.HCBCTree;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class QuadTreeTest {
    private QuadTree[] children = new QuadTree[4];

    @Spy
    private QuadTree tree = new HCBCTree();

    @Before
    public void setUp() {
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
}
