package paramonov.valentin.fiction.hcbc;

import paramonov.valentin.fiction.collections.Pair;
import paramonov.valentin.fiction.hcbc.exception.HCBCDimsDoNotMatchException;
import paramonov.valentin.fiction.image.Image;

import java.util.concurrent.ForkJoinPool;

public class HCBCModule {

    public Pair<HCBCTree, Image> hcbcEncode(
        Image img, double tolerance, int maxLevel) {

        Image imgCopy = new Image(img.getARGB(), img.getWidth(), img.getHeight());

        HCBCTree tree = qtPartition(imgCopy, tolerance, maxLevel);

        return new Pair<>(tree, imgCopy);
    }

    public Image hcbcDecode(HCBCTree tree, Image grayImg) throws HCBCDimsDoNotMatchException {
        if(!dimensionsMatch(tree, grayImg)) {
            throw new HCBCDimsDoNotMatchException();
        }

        Image colorImg = new Image(grayImg.getARGB(), grayImg.getWidth(), grayImg.getHeight());

        int imgW = colorImg.getWidth();
        int[] colors = colorImg.getARGB();

        for(HCBCBlock blk : tree) {
            int startX = blk.getX();
            int endX = startX + blk.getWidth();
            int startY = blk.getY();
            int endY = startY + blk.getHeight();
            double mtcRC = blk.getMtcR();
            double mtcGC = blk.getMtcG();
            double mtcBC = blk.getMtcB();
            double mtcSumSqr = (mtcRC * mtcRC) + (mtcGC * mtcGC) + (mtcBC * mtcBC);
            double mtcR = mtcRC / mtcSumSqr;
            double mtcG = mtcGC / mtcSumSqr;
            double mtcB = mtcBC / mtcSumSqr;

            for(int x = startX; x < endX; x++) {
                for(int y = startY; y < endY; y++) {
                    int index = y * imgW + x;
                    int color = colors[index] & 0xff;
                    int r = (int) Math.round(mtcR * color);
                    r = r > 0xff ? 0xff : r;
                    int g = (int) Math.round(mtcG * color);
                    g = g > 0xff ? 0xff : g;
                    int b = (int) Math.round(mtcB * color);
                    b = b > 0xff ? 0xff : b;

                    colors[index] = (colors[index] & 0xff000000) | r << 16 | g << 8 | b;
                }
            }
        }

        return colorImg;
    }

    private boolean dimensionsMatch(HCBCTree tree, Image img) {
        return tree.getBlockWidth() == img.getWidth() && tree.getBlockHeight() == img.getHeight();
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
            double r = (colors[i] >> 16) & 0xff;
            double g = (colors[i] >> 8) & 0xff;
            double b = colors[i] & 0xff;
            double sum = r + g + b;

            if(sum == 0) continue;

            int x = i % w;
            int y = i / w;

            tc[x][y][0] = r / sum;
            tc[x][y][1] = g / sum;
            tc[x][y][2] = b / sum;

            if(produceGrayImage) {
                int weightedSum = (int) Math.round((r * r + g * g + b * b) / sum);
                weightedSum = weightedSum > 0xff ? 0xff : weightedSum;

                colors[i] = (colors[i] & 0xff000000) | weightedSum << 16 | weightedSum << 8 | weightedSum;
            }
        }

        return tc;
    }


    public HCBCTree qtPartition(Image img, double tolerance, int maxLevel) {
        int w = img.getWidth();
        int h = img.getHeight();
        double[][][] tc = calculateTC(img, true);
        HCBCTree tree = new HCBCTree();
        ForkJoinPool pool = new ForkJoinPool();
        HCBCPartition partitionTask = new HCBCPartition(tree, tc, tolerance, maxLevel, 0, 0, 0, w, h);

        pool.invoke(partitionTask);

        return tree;
    }
}
