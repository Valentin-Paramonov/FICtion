package paramonov.valentin.fiction.image.processor;

import paramonov.valentin.fiction.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageProcessor {
    private ImageProcessor() {}

    public static Image loadImageFromFile(String file) throws IOException {
        BufferedImage buffImg = loadBufferedImage(file);

        int w = buffImg.getWidth();
        int h = buffImg.getHeight();

        int[] colorArray = buffImg.getRGB(0, 0, w, h, null, 0, w);

        return new Image(colorArray, w, h);
    }

    public static void writeImageToFile(Image img, String outputFileName) throws IOException {
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

    static void writeImageToPngFile(Image img, String outputFileName) throws IOException {
        final int width = img.getWidth();
        final int height = img.getHeight();
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        buffImg.setRGB(0, 0, width, height, img.getARGB(), 0, width);

        try(FileOutputStream fos = new FileOutputStream(outputFileName)) {
            ImageIO.write(buffImg, "png", fos);
        }
    }

    static void writeImageToDatFile(Image img, String outputFileName, int width, int height) throws IOException {
        byte[] byteImage = new byte[width * height];
        int[] colorImage = img.getARGB();

        for(int i = 0; i < byteImage.length; i++) {
            byteImage[i] = (byte) (colorImage[i] & 0xff);
        }

        try(FileOutputStream fos = new FileOutputStream(outputFileName, true)) {
            fos.write(byteImage);
        }
    }

    private static BufferedImage loadBufferedImage(String file) throws IOException {
        try(FileInputStream fis = new FileInputStream(file)) {
            return ImageIO.read(fis);
        }
    }
}
