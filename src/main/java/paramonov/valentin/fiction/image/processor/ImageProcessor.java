package paramonov.valentin.fiction.image.processor;

import paramonov.valentin.fiction.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageProcessor {
    ImageProcessor() {}

    public Image loadImageFromFile(String file) throws IOException {
        BufferedImage buffImg = loadBufferedImage(file);

        int w = buffImg.getWidth();
        int h = buffImg.getHeight();

        int[] colorArray = buffImg.getRGB(0, 0, w, h, null, 0, w);

        return new Image(colorArray, w, h);
    }

    public void writeImageToFile(Image img, String outputFileName) throws IOException {
        int w = img.getWidth();
        int h = img.getHeight();

        if(outputFileName.endsWith(".png")) {
            writeImageToPngFile(img, outputFileName, w, h);
        } else if(outputFileName.endsWith(".dat")) {
            writeImageToDatFile(img, outputFileName, w, h);
        } else {
            writeImageToPngFile(img, outputFileName + ".png", w, h);
        }
    }

    void writeImageToPngFile(Image img, String outputFileName, int width, int height) throws IOException {
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        buffImg.setRGB(0, 0, width, height, img.getARGB(), 0, width);

        try(FileOutputStream fos = new FileOutputStream(outputFileName)) {
            ImageIO.write(buffImg, "png", fos);
        }
    }

    void writeImageToDatFile(Image img, String outputFileName, int width, int height) throws IOException {
        byte[] byteImage = new byte[width * height];
        int[] colorImage = img.getARGB();

        for(int i = 0; i < byteImage.length; i++) {
            byteImage[i] = (byte) (colorImage[i] & 0xff);
        }

        try(FileOutputStream fos = new FileOutputStream(outputFileName, true)) {
            fos.write(byteImage);
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

        return new Image(gray, img.getWidth(), img.getHeight());
    }
}
