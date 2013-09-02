package paramonov.valentin.fiction.gui.builder;

import java.awt.Frame;
import java.awt.Button;
import java.awt.event.ActionListener;

public class AppGUIBuilder implements GUIBuilder {
    public Button addButton(
        String tag, ActionListener listener, String actionCommand) {
        
        Button butt = new Button(tag);
        butt.addActionListener(listener);
        butt.setActionCommand(actionCommand);
        
        return butt;
    }
}
