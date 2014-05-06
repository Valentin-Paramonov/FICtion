package paramonov.valentin.fiction.gui;

import paramonov.valentin.fiction.gui.canvas.AppGLCanvas;
import paramonov.valentin.fiction.gui.canvas.operator.CanvasOperatorFactory;
import paramonov.valentin.fiction.gui.canvas.operator.CanvasOperator;
import paramonov.valentin.fiction.gui.canvas.operator.exception.OperationException;
import paramonov.valentin.fiction.gui.dialog.ImageFileDialog;

import java.awt.*;
import java.awt.event.*;

import static paramonov.valentin.fiction.gui.builder.Component.MAIN_PANEL;
import static paramonov.valentin.fiction.gui.builder.Component.OPTION_PANEL;

public class App extends Frame implements WindowListener, ItemListener, TextListener {
    private static final long serialVersionUID = 0xFADE;
    private static final String TITLE = "FICtion";
    public static final int CANVAS_WIDTH = 768;
    public static final int CANVAS_HEIGHT = 432;

    private CanvasOperator canvasOperator;
    private FileDialog openDialog;
    private FileDialog saveDialog;

    public App() {
        super(TITLE);
        init();
    }

    private void init() {
        this.addWindowListener(this);
        buildGUI();
        setupCanvasOperator();
        attachDialogs();
    }

    public void launch() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void buildGUI() {
        new AppGUIBuilder().buildGUI(this);
    }

    private void setupCanvasOperator() {
        canvasOperator = CanvasOperatorFactory.createCanvasOperator(getAppGLCanvas());
    }

    private void attachDialogs() {
        openDialog = new ImageFileDialog(this, "Open file", FileDialog.LOAD);
        saveDialog = new ImageFileDialog(this, "Save to...", FileDialog.SAVE);
    }

    private AppGLCanvas getAppGLCanvas() {
        Container mainPanel = (Container) getComponent(0);
        Container canvasPanel = (Container) mainPanel.getComponent(0);

        return (AppGLCanvas) canvasPanel.getComponent(0);
    }

    void loadImageToCanvas() {
        openDialog.setVisible(true);

        String fileName = openDialog.getFile();

        if(fileName == null) return;

        try {
            canvasOperator.loadImage(openDialog.getDirectory() + fileName);
        } catch(OperationException oe) {
            oe.printStackTrace();
        }
    }

    void openOptionPane() {
        CardLayout cl = (CardLayout) this.getLayout();

        cl.show(this, OPTION_PANEL.toString());

        this.setTitle(TITLE + ": Options");
    }

    void closeOptionPane() {
        CardLayout cl = (CardLayout) this.getLayout();

        cl.show(this, MAIN_PANEL.toString());
        canvasOperator.update();
        this.setTitle(TITLE);
    }

    void start() {}

    void pause() {}

    void stop() {}

    public void itemStateChanged(ItemEvent ie) {
    }

    public void textValueChanged(TextEvent te) {
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {
        canvasOperator.update();
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {
        canvasOperator.update();
    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {
        canvasOperator.update();
    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
        canvasOperator.update();
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {
        canvasOperator.update();
    }
}
