package paramonov.valentin.fiction.gui.builder;

import java.awt.Frame;
import java.awt.Button;
import java.awt.event.ActionListener;

public interface GUIBuilder {
    Button addButton(
        String tag, ActionListener listener, String actionCommand);
}
