package paramonov.valentin.fiction.fic;

import paramonov.valentin.fiction.collections.QuadTree;

import java.util.Iterator;

public class FICTree extends QuadTree<FICTree,RangeBlock> {
    private Quantizer hQuantizer;
    private Quantizer vQuantizer;

    public FICTree() {}

    protected void init() {
        RangeBlock element = getElement();
        final int x = element.getX();
        final int y = element.getY();
        final int w = element.getW();
        final int h = element.getH();
        hQuantizer = new Quantizer(2, x, x + w);
        vQuantizer = new Quantizer(2, y, y + h);
    }

    @Override
    protected int findPlace(RangeBlock rangeBlock) {
        if(elementDoesNotFit(rangeBlock)) {
            return -1;
        }

        final int rangeX = rangeBlock.getX();
        final int rangeY = rangeBlock.getY();

        return hQuantizer.quantize(rangeX) + 2 * vQuantizer.quantize(rangeY);
    }

    private boolean elementDoesNotFit(RangeBlock rangeBlock) {
        RangeBlock element = getElement();
        final int rangeX = rangeBlock.getX();
        final int rangeY = rangeBlock.getY();
        final int rangeW = rangeBlock.getW();
        final int rangeH = rangeBlock.getH();
        final int elementX = element.getX();
        final int elementY = element.getY();
        final int elementW = element.getW();
        final int elementH = element.getH();

        return !(rangeFits(rangeX, rangeW, elementX, elementW) && rangeFits(rangeY, rangeH, elementY, elementH));
    }

    private boolean rangeFits(int rangeLeft, int rangeW, int elementLeft, int elementW) {
        final int elementRightBoundary = elementLeft + elementW;
        final int rangeRightBoundary = rangeLeft + rangeW;
        if(inRange(elementLeft, elementRightBoundary, rangeLeft) && rangeRightBoundary <= elementRightBoundary) {
            return true;
        }

        return false;
    }

    boolean inRange(int rangeMin, int rangeMax, int value) {
        return value >= rangeMin && value <= rangeMax;
    }
}
