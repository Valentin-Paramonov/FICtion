package paramonov.valentin.fiction.image.processor;

import paramonov.valentin.fiction.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class ImageProcessor {
    public Image loadImageFromFile(String file)
        throws FileNotFoundException {

        BufferedImage buffImg = loadBufferedImage(file);
        int w = buffImg.getWidth();
        int h = buffImg.getHeight();


        int[] colorArray = buffImg.getRGB(0, 0, w, h, null, 0, w);

        return new Image(colorArray, w, h);

//        WritableRaster raster = Raster.createInterleavedRaster(
//            DataBuffer.TYPE_BYTE, buffImg.getWidth(), buffImg.getHeight(), 4, null
//        );
//
//        ComponentColorModel colormodel = new ComponentColorModel(
//                ColorSpace.getInstance(ColorSpace.CS_sRGB),
//                new int[] {8,8,8,8},
//                true,
//                false,
//                ComponentColorModel.TRANSLUCENT,
//                DataBuffer.TYPE_BYTE
//        );
//
//        img = new BufferedImage(
//                colormodel,
//                raster,
//                false,
//                null
//        );
//
//        Graphics2D graphics = img.createGraphics();
//
//        //graphics.drawImage(buffImg, null, null);
//        graphics.drawImage(
//                buffImg,
//                new AffineTransform(new double[]{1,0,0,-1,0,h}),
//                null);
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
