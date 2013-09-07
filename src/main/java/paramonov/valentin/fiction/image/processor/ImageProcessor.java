package paramonov.valentin.fiction.image.processor;

import paramonov.valentin.fiction.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class ImageProcessor {
    public Image loadImageFromFile(String file)
        throws FileNotFoundException {

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

    private BufferedImage loadBufferedImage(String file) {
        BufferedImage buffImg = null;

        try(FileInputStream fis =
            new FileInputStream(file)) {

            buffImg = ImageIO.read(fis);

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        return buffImg;
    }
}
