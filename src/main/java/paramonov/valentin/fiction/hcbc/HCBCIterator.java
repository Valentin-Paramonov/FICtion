package paramonov.valentin.fiction.hcbc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class HCBCIterator implements Iterator<HCBCBlock> {
    private Iterator<HCBCBlock> iterator;

    HCBCIterator(HCBCTree tree) {
        init(tree);
    }

    private void init(HCBCTree tree) {
        List<HCBCBlock> blockList = new ArrayList<>(tree.size());

        if(tree.size() != 0) {
            treeToArray(tree, blockList);
        }

        iterator = blockList.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public HCBCBlock next() throws NoSuchElementException {
        return iterator.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private void treeToArray(HCBCTree tree, List<HCBCBlock> blocks) {
        if(!tree.hasChildren()) {
            blocks.add(tree.getElement());
            return;
        }

        HCBCTree[] children = (HCBCTree[]) tree.getChildren();

        treeToArray(children[0], blocks);
        treeToArray(children[1], blocks);
        treeToArray(children[2], blocks);
        treeToArray(children[3], blocks);
    }
}
