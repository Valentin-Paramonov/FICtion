package paramonov.valentin.fiction.gui.builder;

import java.awt.*;
import java.awt.event.ActionListener;

public interface GUIBuilder<F extends Frame> {
    void buildGUI(F frame);

    Button createButton(
        String tag, ActionListener listener, String actionCommand);

    Panel createPanel(LayoutManager layout);
}
