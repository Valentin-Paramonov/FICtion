package paramonov.valentin.fiction.gui;

import paramonov.valentin.fiction.gui.builder.GUIBuilder;
import paramonov.valentin.fiction.gui.canvas.AppGLCanvas;
import paramonov.valentin.fiction.gui.canvas.CanvasGLFramebufferProcessor;
import paramonov.valentin.fiction.gui.canvas.CanvasGLImageProcessor;
import paramonov.valentin.fiction.gui.canvas.CanvasGLTextureProcessor;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private final class DoubleTextFieldActionListener implements ActionListener {
        private final TextField source;
        private final double minValue;
        private final double maxValue;
        private String previousValue;

        private DoubleTextFieldActionListener(TextField source, double minValue, double maxValue, String initialText) {
            this.source = source;
            this.minValue = minValue;
            this.maxValue = maxValue;
            previousValue = initialText;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            final String text = source.getText();
            final double newValue;
            try {
                newValue = Double.parseDouble(text);
            } catch(NumberFormatException nfe) {
                source.setText(previousValue);
                return;
            }

            if(newValue < minValue || newValue > maxValue) {
                source.setText(previousValue);
                return;
            }

            previousValue = text;
        }
    }

    private final class IntegerTextFieldActionListener implements ActionListener {
        private final TextField source;
        private final int minValue;
        private final int maxValue;
        private String previousValue;

        private IntegerTextFieldActionListener(TextField source, int minValue, int maxValue, String initialValue) {
            this.source = source;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.previousValue = initialValue;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            final String text = source.getText();
            final int newValue;
            try {
                newValue = Integer.parseInt(text);
            } catch(NumberFormatException nfe) {
                source.setText(previousValue);
                return;
            }

            if(newValue < minValue || newValue > maxValue) {
                source.setText(previousValue);
                return;
            }

            previousValue = text;
        }
    }

    @Override
    public void buildGUI(App app) {
        // app.setMinimumSize(new Dimension(900, 450));
        // app.setPreferredSize(new Dimension(900,450));
        app.setLayout(new CardLayout());

        buildMainPanel(app);

        buildOptionPanel(app);
    }

    private void buildOptionPanel(final App app) {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        Panel optionPanel = new Panel(layout);
        optionPanel.setBackground(Color.WHITE);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.insets = new Insets(10, 10, 5, 10);
        final Panel ficPanel = buildFICPanel(app);
        optionPanel.add(ficPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.insets = new Insets(5, 10, 10, 10);
        final Panel ficGAPanel = buildFICGAPanel(app);
        optionPanel.add(ficGAPanel, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 0.1;
        final Panel optionButtonsPanel = buildOptionConfirmationButtonsPanel(app);
        optionPanel.add(optionButtonsPanel, constraints);

        app.add(optionPanel, OPTION_PANEL.name());
    }

    private Panel buildFICPanel(final App app) {
        final Panel ficPanel = new Panel(new GridBagLayout());
        ficPanel.setBackground(Color.DARK_GRAY);
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10,10,10,10);
        constraints.weightx = 1;
        final Panel ficOptionPanel = createGridPanel(0, 2, 0, 0);
        ficOptionPanel.setBackground(Color.WHITE);

        final Panel minSubdivisions = buildIntegerTextField("Minimal Subdivisions:", 1, 16, 3);
        final Panel maxSubdivisions = buildIntegerTextField("Maximal Subdivisions:", 1, 16, 5);
        final Panel tolerance = buildDoubleTextField("Tolerance:", 0, 1, 0.125);
        final Panel minContrast = buildDoubleTextField("Minimal Contrast Value:", -10, 10, -1);
        final Panel maxContrast = buildDoubleTextField("Maximal Contrast Value:", -10, 10, 1);
        final Panel contrastLevels = buildIntegerTextField("Contrast Levels:", 0, 65535, 32);
        final Panel minBrightness = buildDoubleTextField("Minimal Brightness Value:", -255, 255, -255);
        final Panel maxBrightness = buildDoubleTextField("Maximal Brightness Value:", -255, 255, 255);
        final Panel brightnessLevels = buildIntegerTextField("Brightness Levels:", 0, 65535, 128);

        ficOptionPanel.add(minSubdivisions);
        ficOptionPanel.add(maxSubdivisions);
        ficOptionPanel.add(tolerance);
        ficOptionPanel.add(minContrast);
        ficOptionPanel.add(maxContrast);
        ficOptionPanel.add(contrastLevels);
        ficOptionPanel.add(minBrightness);
        ficOptionPanel.add(maxBrightness);
        ficOptionPanel.add(brightnessLevels);

        ficPanel.add(ficOptionPanel,constraints);

        return ficPanel;
    }

    private Panel buildFICGAPanel(App app) {
        final Panel gaPanel = new Panel(new GridBagLayout());
        gaPanel.setBackground(Color.DARK_GRAY);
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10,10,10,10);
        constraints.weightx = 1;
        final Panel gaOptionPanel = createGridPanel(0, 2, 0, 0);
        gaOptionPanel.setBackground(Color.WHITE);

        final Panel populationSize = buildIntegerTextField("Population Size:", 1, 10000, 50);
        final Panel crossoverProbability = buildDoubleTextField("Crossover Probability:", 0, 1, 0.8);
        final Panel mutationProbability = buildDoubleTextField("Mutation Probability:", 0, 1, 0.05);
        final Panel tolerance = buildDoubleTextField("Tolerance:", 0, 1, 0.01);

        gaOptionPanel.add(populationSize);
        gaOptionPanel.add(crossoverProbability);
        gaOptionPanel.add(mutationProbability);
        gaOptionPanel.add(tolerance);
        gaPanel.add(gaOptionPanel,constraints);

        return gaPanel;
    }

    private Panel buildOptionConfirmationButtonsPanel(final App app) {
        final Panel panel = new Panel();

        final Button closeOptions = createButton("CLOSE");
        closeOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                app.closeOptionPane();
            }
        });

        panel.add(closeOptions);

        return panel;
    }

    private void buildMainPanel(App app) {
        Panel mainPanel = createFlowPanel();
        mainPanel.setBackground(Color.WHITE);

        app.add(mainPanel, MAIN_PANEL.toString());

        mainPanel.add(buildCanvasPanel());

        mainPanel.add(buildButtonPanel(app));
    }

    private Panel buildCanvasPanel() {
        final CanvasGLTextureProcessor textureProcessor = new CanvasGLTextureProcessor();
        final CanvasGLFramebufferProcessor framebufferProcessor = new CanvasGLFramebufferProcessor(textureProcessor);
        final CanvasGLImageProcessor imageProcessor =
            new CanvasGLImageProcessor(textureProcessor, framebufferProcessor);
        GLCanvas canvas = new AppGLCanvas(textureProcessor, framebufferProcessor, imageProcessor);

        canvas.setSize(new Dimension(App.CANVAS_WIDTH, App.CANVAS_HEIGHT));

        Panel canvasPanel = createFlowPanel();
        canvasPanel.add(canvas, CANVAS.name(), 0);

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

        operationButtonPan.add(createOperationButtonsPanel(app));

        optionButtonPan.add(buildOptionButtonsPanel(app));

        return buttonPanel;
    }

    private Panel buildOptionButtonsPanel(final App app) {
        Panel buttonPanel = createGridPanel(0, 1, 2, 2);
        buttonPanel.setBackground(Color.WHITE);

        final Button loadImage = createButton("LOAD IMAGE");
        loadImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                app.loadImageToCanvas();
            }
        });

        final Button saveImage = createButton("SAVE IMAGE");

        final Button openOptions = createButton("OPTIONS");
        openOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                app.openOptionPane();
            }
        });

        buttonPanel.add(loadImage);
        buttonPanel.add(saveImage);
        buttonPanel.add(openOptions);

        return buttonPanel;
    }

    private Panel createOperationButtonsPanel(final App app) {
        Panel buttons = createGridPanel(0, 1, 2, 2);
        buttons.setBackground(Color.WHITE);

        final Button start = createButton("START");
        final Button pause = createButton("PAUSE");
        final Button stop = createButton("STOP");

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                app.start();
            }
        });
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                app.pause();
            }
        });
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                app.stop();
            }
        });

        buttons.add(start);
        buttons.add(pause);
        buttons.add(stop);

        return buttons;
    }

    @Override
    public Button createButton(String tag) {

        Button butt = new Button(tag);

        butt.setBackground(Color.DARK_GRAY);
        butt.setForeground(Color.WHITE);

        return butt;
    }

    @Override
    public Panel createPanel(LayoutManager layout) {
        return new Panel(layout);
    }

    private Panel createFlowPanel() {
        Panel panel = createPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        panel.setBackground(Color.DARK_GRAY);

        return panel;
    }

    private Panel createGridPanel(int rows, int cols, int hgap, int vgap) {
        final GridLayout layout = new GridLayout(rows, cols, hgap, vgap);
        return createPanel(layout);
    }

    private Panel buildDoubleTextField(String labelText, double minValue, double maxValue, double value) {
        final FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 5);
        final Panel panel = new Panel(layout);
        final Panel gridPanel = createGridPanel(1, 2, 0, 0);

        final Label label = new Label(labelText);

        final String text = String.valueOf(value);
        final TextField textField = new TextField(text);
        final ActionListener listener = new DoubleTextFieldActionListener(textField, minValue, maxValue, text);
        textField.addActionListener(listener);

        gridPanel.add(label);
        gridPanel.add(textField);
        panel.add(gridPanel);

        return panel;
    }

    private Panel buildIntegerTextField(String labelText, int minValue, int maxValue, int value) {
        final FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 5);
        final Panel panel = new Panel(layout);
        final Panel gridPanel = createGridPanel(1, 2, 0, 0);

        final Label label = new Label(labelText);

        final String text = String.valueOf(value);
        final TextField textField = new TextField(text);
        final ActionListener listener = new IntegerTextFieldActionListener(textField, minValue, maxValue, text);
        textField.addActionListener(listener);

        gridPanel.add(label);
        gridPanel.add(textField);
        panel.add(gridPanel);

        return panel;
    }
}
