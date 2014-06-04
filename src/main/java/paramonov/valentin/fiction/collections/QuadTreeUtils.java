package paramonov.valentin.fiction.collections;

import paramonov.valentin.fiction.image.Image;

public final class QuadTreeUtils {
    public static <T extends QuadTree<T, E>, E extends Block> Image visualize(QuadTree<T, E> tree) {
        final Block element = tree.getElement();
        final int width = element.getWidth();
        final int height = element.getHeight();
        final Image visualization = new Image(width, height);

        for(QuadTree<T, E> subTree : tree) {
            if(subTree.hasChildren()) {
                continue;
            }

            final E block = subTree.getElement();
            final int x = block.getX();
            final int y = block.getY();
            final int blockWidth = block.getWidth();
            final int blockHeight = block.getHeight();
            final int endX = x + blockWidth;
            final int endY = y + blockHeight;

            for(int j = y; j < endY; j++) {
                for(int i = x; i < endX; i++) {

                    if(i == x || i == endX - 1 || j == y || j == endY - 1) {
                        visualization.setColor(i, j, 0xff000000);
                    } else {
                        visualization.setColor(i, j, 0xffffffff);
                    }
                }
            }
        }

        return visualization;
    }
}
