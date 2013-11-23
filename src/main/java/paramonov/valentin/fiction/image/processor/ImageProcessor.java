package paramonov.valentin.fiction.image.processor;

import paramonov.valentin.fiction.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageProcessor {
    ImageProcessor() {}

    public Image loadImageFromFile(String file)
        throws IOException {

        BufferedImage buffImg = loadBufferedImage(file);

        int w = buffImg.getWidth();
        int h = buffImg.getHeight();

        int[] colorArray = buffImg.getRGB(0, 0, w, h, null, 0, w);

        return new Image(colorArray, w, h);
    }

    public void writeImageToFile(Image img, String file)
        throws IOException {

        int w = img.getWidth();
        int h = img.getHeight();
        String pngFile =
            file.endsWith(".png") ?
                file : file + ".png";

        BufferedImage buffImg =
            new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        buffImg.setRGB(0, 0, w, h, img.getARGB(), 0, w);

        try(FileOutputStream fos =
                new FileOutputStream(pngFile)) {

            ImageIO.write(buffImg, "png", fos);
        }
    }

    private BufferedImage loadBufferedImage(String file) throws IOException {
        try(FileInputStream fis = new FileInputStream(file)) {
            return ImageIO.read(fis);
        }
    }

    public Image toGrayscale(Image img) {
        int[] gray = img.getARGB();

        for(int i = 0; i < gray.length; i++) {
            int color = gray[i];

            int r = (color >> 16) & 0xff;
            int g = (color >> 8) & 0xff;
            int b = color & 0xff;

            int luma = (int) (.2126 * r + .7153 * g + .0721 * b);

            gray[i] = (color & 0xff000000) | (luma << 16) | (luma << 8) | luma;
        }

        return new Image(
            gray, img.getWidth(), img.getHeight());
    }
}
