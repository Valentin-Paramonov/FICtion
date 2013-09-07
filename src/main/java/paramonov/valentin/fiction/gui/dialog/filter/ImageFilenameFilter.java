package paramonov.valentin.fiction.gui.dialog.filter;

import java.io.File;
import java.io.FilenameFilter;

class ImageFilenameFilter implements FilenameFilter {
    @Override
    public boolean accept(File file, String name) {
        return (
            name.toLowerCase().endsWith(".png") ||
            name.toLowerCase().endsWith(".jpg") ||
            name.toLowerCase().endsWith(".bmp"));
    }
}
