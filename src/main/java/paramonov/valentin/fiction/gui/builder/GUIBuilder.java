package paramonov.valentin.fiction.gui.builder;

import java.awt.*;

public interface GUIBuilder<F extends Frame> {
    void buildGUI(F frame);

    Button createButton(String tag);

    Panel createPanel(LayoutManager layout);
}
