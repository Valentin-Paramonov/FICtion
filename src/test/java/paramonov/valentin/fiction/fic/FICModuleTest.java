package paramonov.valentin.fiction.fic;

import org.junit.Before;
import org.junit.Test;
import paramonov.valentin.fiction.image.Image;

public class FICModuleTest {
    private static final FICProperties PROPERTIES = new FICProperties();
    private FICModule ficModule;

    static {
        PROPERTIES.setBrightnessLevels(128);
        PROPERTIES.setContrastLevels(32);
        PROPERTIES.setDomainStep(1);
        PROPERTIES.setMinSubdivisions(1);
        PROPERTIES.setMaxSubdivisions(1);
        PROPERTIES.setTolerance(0.1);
    }

    @Before
    public void setUp() {
        ficModule = new FICModule(PROPERTIES);
    }

    @Test
    public void testEncode_ShouldNotFail() {
        final int[] colorArray = {1, 2, 3, 4};
        final Image image = new Image(colorArray, 2, 2);

        ficModule.encode(image);
    }
}
