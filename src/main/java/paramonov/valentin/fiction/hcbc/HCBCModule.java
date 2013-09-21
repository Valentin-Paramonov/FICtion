package paramonov.valentin.fiction.hcbc;

import paramonov.valentin.fiction.collections.Pair;
import paramonov.valentin.fiction.image.Image;

public class HCBCModule {
    public Pair<HCBCTree, Image> hcbcEncode(Image img) {
        HCBCTree tree = qtPartition(img);

        return new Pair<>(tree, img);
    }

    public double[][] calculateTC(Image img) {
        return calculateTC(img, false);
    }

    public double[][] calculateTC(Image img, boolean produceGrayImage) {
        int[] colors = img.getARGB();

        double[][] tc = new double[colors.length][3];

        for(int i = 0; i < colors.length; i++) {
            int r = (colors[i] >> 16) & 0xff;
            int g = (colors[i] >> 8) & 0xff;
            int b = colors[i] & 0xff;
            double sum = r + g + b;

            if(sum == 0) continue;

            tc[i][0] = r / sum;
            tc[i][1] = g / sum;
            tc[i][2] = b / sum;

            if(produceGrayImage) {
                int weightedSum =
                    (int) (r * tc[i][0] + g * tc[i][1] + b * tc[i][2]);

                colors[i] =
                    (colors[i] & 0xff000000) |
                    weightedSum << 16 | weightedSum << 8 | weightedSum;
            }
        }

        return tc;
    }


    public HCBCTree qtPartition(Image img) {
        double[][] tc = calculateTC(img, true);
        return null;
    }
}
