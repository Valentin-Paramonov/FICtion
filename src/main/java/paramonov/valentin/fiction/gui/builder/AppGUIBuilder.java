package paramonov.valentin.fiction.gui.builder;

import paramonov.valentin.fiction.gui.App;
import paramonov.valentin.fiction.gui.canvas.AppGLCanvas;

import javax.media.opengl.awt.GLCanvas;
import java.awt.*;
import java.awt.event.ActionListener;

import static paramonov.valentin.fiction.gui.action.Action.*;
import static paramonov.valentin.fiction.gui.builder.Component.*;

public class AppGUIBuilder implements GUIBuilder<App> {
    /*
    * /this
    * |-mainpan
    * . |-canpan
    * . . |-canvas
    * . |-buttpanpan
    * .   |-buttpan
    * .     |-open
    * .     |-strt
    * .     |-paus
    * .     |-stop
    * .     |-save
    * |-optpan
    *
    */

    @Override
    public void buildGUI(App app) {
        // app.setMinimumSize(new Dimension(900, 450));
        // app.setPreferredSize(new Dimension(900,450));
        app.setLayout(new CardLayout());

        buildMainPanel(app);

        buildOptionPanel(app);
    }

    private void buildOptionPanel(App app) {
        Panel optionPanel = createGridPanel(3, 2, 10, 10);

        optionPanel.add(
            createButton("CLOSE", app, CLOSE_OPTIONS.toString()));

        app.add(optionPanel, OPTION_PANEL.toString());
    }

    private void buildMainPanel(App app) {
        Panel mainPanel = createFlowPanel();
        mainPanel.setBackground(Color.WHITE);

        app.add(mainPanel, MAIN_PANEL.toString());

        mainPanel.add(
            buildCanvasPanel());

        mainPanel.add(
            buildButtonPanel(app));
    }

    private Panel buildCanvasPanel() {
        Panel canvasPanel = createFlowPanel();
        GLCanvas canvas = new AppGLCanvas();

        canvas.setSize(
            new Dimension(
                App.CANVAS_WIDTH,
                App.CANVAS_HEIGHT));

        canvasPanel.add(
            canvas,
            CANVAS.toString(),
            0);

        return canvasPanel;
    }

    private Panel buildButtonPanel(App app) {
        Panel buttonPanel = createFlowPanel();

        Panel centralPanel = createGridPanel(2, 1, 0, 10);

        Panel operationButtonPan = createFlowPanel();
        operationButtonPan.setBackground(Color.WHITE);

        Panel optionButtonPan = createFlowPanel();
        optionButtonPan.setBackground(Color.WHITE);

        centralPanel.add(operationButtonPan);
        centralPanel.add(optionButtonPan);

        buttonPanel.add(centralPanel);

        operationButtonPan.add(
            createOperationButtonsPanel(app));

        optionButtonPan.add(
            buildOptionButtonsPanel(app));

        return buttonPanel;
    }

    private Panel buildOptionButtonsPanel(App app) {
        Panel buttonPanel = createGridPanel(0, 1, 2, 2);
        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(
            createButton(
                "LOAD IMAGE", app, LOAD_IMAGE.toString()));
        buttonPanel.add(
            createButton(
                "OPTIONS", app, OPEN_OPTIONS.toString()));

        return buttonPanel;
    }

    private Panel createOperationButtonsPanel(App app) {
        Panel buttons = createGridPanel(0, 1, 2, 2);
        buttons.setBackground(Color.WHITE);

        buttons.add(createButton("START", app, "START"));
        buttons.add(createButton("PAUSE", app, "PAUSE"));
        buttons.add(createButton("STOP", app, "STOP"));

        return buttons;
    }

    @Override
    public Button createButton(
        String tag, ActionListener listener, String actionCommand) {

        Button butt = new Button(tag);
        butt.addActionListener(listener);
        butt.setActionCommand(actionCommand);

        butt.setBackground(Color.DARK_GRAY);
        butt.setForeground(Color.WHITE);

        return butt;
    }

    @Override
    public Panel createPanel(LayoutManager layout) {
        return new Panel(layout);
    }

    private Panel createFlowPanel() {
        Panel panel = createPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 10));

        panel.setBackground(Color.DARK_GRAY);

        return panel;
    }

    private Panel createGridPanel(
        int rows, int cols, int hgap, int vgap) {

        return createPanel(
            new GridLayout(rows, cols, hgap, vgap));
    }
}
