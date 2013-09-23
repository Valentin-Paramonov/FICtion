package paramonov.valentin.fiction.hcbc;

import paramonov.valentin.fiction.collections.Pair;
import paramonov.valentin.fiction.image.Image;

import java.util.concurrent.ForkJoinPool;

public class HCBCModule {
    public Pair<HCBCTree, Image> hcbcEncode(
        Image img, double tolerance, int maxLevel) {

        Image imgCopy =
            new Image(
                img.getARGB(), img.getWidth(), img.getHeight());

        HCBCTree tree = qtPartition(imgCopy, tolerance, maxLevel);

        return new Pair<>(tree, imgCopy);
    }

    public double[][][] calculateTC(Image img) {
        return calculateTC(img, false);
    }

    public double[][][] calculateTC(Image img, boolean produceGrayImage) {
        int[] colors = img.getARGB();
        int w = img.getWidth();
        int h = img.getHeight();
        double[][][] tc = new double[w][h][3];

        for(int i = 0; i < colors.length; i++) {
            int r = (colors[i] >> 16) & 0xff;
            int g = (colors[i] >> 8) & 0xff;
            int b = colors[i] & 0xff;
            double sum = r + g + b;

            if(sum == 0) continue;

            int x = i % w;
            int y = i / w;

            tc[x][y][0] = r / sum;
            tc[x][y][1] = g / sum;
            tc[x][y][2] = b / sum;

            if(produceGrayImage) {
                int weightedSum =
                    (int) (r * tc[x][y][0] + g * tc[x][y][1] + b * tc[x][y][2]);

                colors[i] =
                    (colors[i] & 0xff000000) |
                    weightedSum << 16 | weightedSum << 8 | weightedSum;
            }
        }

        return tc;
    }


    public HCBCTree qtPartition(
        Image img, double tolerance, int maxLevel) {

        int w = img.getWidth();
        int h = img.getHeight();
        double[][][] tc = calculateTC(img, true);
        HCBCTree tree = new HCBCTree();
        ForkJoinPool pool = new ForkJoinPool();
        HCBCPartition partitionTask =
            new HCBCPartition(
                tree, tc, tolerance, maxLevel, 0, 0, w, h);

        pool.invoke(partitionTask);

        return tree;
    }
}
