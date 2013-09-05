package paramonov.valentin.fiction.image.processor;

public final class ImageProcessorProvider {
    private ImageProcessorProvider() {}

    private static final ImageProcessor IMAGE_PROCESSOR =
        new ImageProcessor();

    public static ImageProcessor getImageProcessor() {
        return IMAGE_PROCESSOR;
    }
}
