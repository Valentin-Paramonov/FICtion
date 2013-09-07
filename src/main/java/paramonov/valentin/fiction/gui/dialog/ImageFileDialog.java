package paramonov.valentin.fiction.gui.dialog;

import paramonov.valentin.fiction.gui.dialog.filter.FilenameFilterProvider;

import java.awt.*;

public class ImageFileDialog extends FileDialog {
    public ImageFileDialog(
        Frame parent, String title, int mode) {

        super(parent, title, mode);

        init();
    }

    private void init() {
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);

        String userHome = System.getProperty("user.home");

        setDirectory(userHome);
        setFilenameFilter(
            FilenameFilterProvider.getImageFilenameFilter());
    }
}
