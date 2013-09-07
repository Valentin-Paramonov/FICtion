package paramonov.valentin.fiction.gui.dialog.filter;

import java.io.FilenameFilter;

public final class FilenameFilterProvider {
    private FilenameFilterProvider() {}

    private static final FilenameFilter IMAGE_FILTER =
        new ImageFilenameFilter();

    public static FilenameFilter getImageFilenameFilter() {
        return IMAGE_FILTER;
    }
}
