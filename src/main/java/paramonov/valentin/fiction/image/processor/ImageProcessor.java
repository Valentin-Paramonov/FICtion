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
            writeImageToPngFile(img, outputFileName);
        } else if(outputFileName.endsWith(".dat")) {
            writeImageToDatFile(img, outputFileName, w, h);
        } else {
            writeImageToPngFile(img, outputFileName + ".png");
        }
    }

    void writeImageToPngFile(Image img, String outputFileName) throws IOException {
        final int width = img.getWidth();
        final int height = img.getHeight();
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

    public double psnr(Image original, Image comparable) {
        double mse = 0;

        int[] originalRGB = original.getARGB();
        int[] comparableRGB = comparable.getARGB();

        if(originalRGB.length != comparableRGB.length) {
            return -1;
        }

        for(int i = 0; i < originalRGB.length; i++) {
            int originalR = (originalRGB[i] >> 16) & 0xff;
            int comparableR = (comparableRGB[i] >> 16) & 0xff;
            int originalG = (originalRGB[i] >> 8) & 0xff;
            int comparableG = (comparableRGB[i] >> 8) & 0xff;
            int originalB = originalRGB[i] & 0xff;
            int comparableB = comparableRGB[i] & 0xff;

            double diffR = originalR - comparableR;
            double diffG = originalG - comparableG;
            double diffB = originalB - comparableB;

            mse += diffR * diffR + diffG * diffG + diffB * diffB;
        }

        mse /= 3 * originalRGB.length;
        double psnr = 10 * Math.log10((255 * 255) / mse);

        return psnr;
    }
}
