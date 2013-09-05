package paramonov.valentin.fiction.gui.builder;

import paramonov.valentin.fiction.gui.App;
import paramonov.valentin.fiction.gui.canvas.AppGLCanvas;

import javax.media.opengl.awt.GLCanvas;
import java.awt.*;
import java.awt.event.ActionListener;

import static paramonov.valentin.fiction.gui.action.Action.*;
import static paramonov.valentin.fiction.gui.builder.Component.CANVAS;
import static paramonov.valentin.fiction.gui.builder.Component.OPTION_PANEL;

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
//        app.setMinimumSize(new Dimension(900, 450));
//        frame.setPreferredSize(new Dimension(900,450));
        app.setLayout(new CardLayout());

        buildMainPanel(app);

        buildOptionPanel(app);

//         FilenameFilter ff = new FilenameFilter() {
//             public boolean accept(File dir, String name) {
//                 return (
//                     name.toLowerCase().endsWith(".png") ||
//                     name.toLowerCase().endsWith(".jpg") ||
//                     name.toLowerCase().endsWith(".bmp")
//                 );
//             }
//         };
//
//         fds = new FileDialog(this, "Save to...", FileDialog.SAVE);
//         fds.setDirectory(System.getProperty("user.home"));
//         fds.setFilenameFilter(ff);
//
//         fdo = new FileDialog(this, "Open file", FileDialog.LOAD);
//         fdo.setDirectory(System.getProperty("user.home"));
//         fdo.setFilenameFilter(ff);
    }

    private void buildOptionPanel(App frame) {
        Panel optionPanel = createGridPanel(3, 2, 10, 10);

        optionPanel.add(
            createButton("CLOSE", frame, CLOSE_OPTIONS.toString()));

        frame.add(optionPanel, OPTION_PANEL.toString());
    }

    private void buildMainPanel(App frame) {
        Panel mainPanel = createFlowPanel();
        mainPanel.setBackground(Color.WHITE);

        frame.add(mainPanel);

        mainPanel.add(
            buildCanvasPanel());

        mainPanel.add(
            buildButtonPanel(frame));
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

    private Panel buildButtonPanel(App frame) {
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
            createOperationButtonsPanel(frame));

        optionButtonPan.add(
            buildOptionButtonsPanel(frame));

        return buttonPanel;
    }

    private Panel buildOptionButtonsPanel(App frame) {
        Panel buttonPanel = createGridPanel(0, 1, 2, 2);
        buttonPanel.setBackground(Color.WHITE);

        AppGLCanvas canvas = getAppGLCanvas(frame);

        buttonPanel.add(
            createButton(
                "LOAD IMAGE", canvas, LOAD_IMAGE.toString()));
        buttonPanel.add(
            createButton(
                "OPTIONS", frame, OPEN_OPTIONS.toString()));

        return buttonPanel;
    }

    private AppGLCanvas getAppGLCanvas(App frame) {
        Container mainPanel =
            (Container) frame.getComponent(0);

        Container canvasPanel =
            (Container) mainPanel.getComponent(0);

        return
            (AppGLCanvas) canvasPanel.getComponent(0);
    }

    private Panel createOperationButtonsPanel(App frame) {
        Panel buttons = createGridPanel(0, 1, 2, 2);
        buttons.setBackground(Color.WHITE);

        buttons.add(createButton("START", frame, "START"));
        buttons.add(createButton("PAUSE", frame, "PAUSE"));
        buttons.add(createButton("STOP", frame, "STOP"));

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
