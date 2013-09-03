package paramonov.valentin.fiction.gui.builder;

import paramonov.valentin.fiction.gui.App;

import java.awt.*;
import java.awt.event.ActionListener;

import static paramonov.valentin.fiction.gui.action.Action.CLOSE_OPTIONS;
import static paramonov.valentin.fiction.gui.action.Action.OPEN_OPTIONS;
import static paramonov.valentin.fiction.gui.builder.PanelEnum.MAIN_PANEL;
import static paramonov.valentin.fiction.gui.builder.PanelEnum.OPTION_PANEL;

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
        app.setMinimumSize(new Dimension(900, 450));
//        frame.setPreferredSize(new Dimension(900,450));
        app.setLayout(new CardLayout());

        buildMainPanel(app);

        buildOptionPanel(app);



//         opts = new Button("Options");
//         opts.addActionListener(this);
//         opts.setActionCommand("Options");
//         buttpan.add(opts);
//
//         optpan.add(new Label("Population size:"));
//
//         popsize = new Choice();
//         popsize.setBackground(Color.white);
//         popsize.setName("Population Size");
//         for(int i = 0x8; i <= 0x80; i++) {
//             popsize.add(Integer.toString(i));
//         }
//         popsize.select("64");
//         popsize.addItemListener(this);
//         optpan.add(popsize);
//
//         optpan.add(new Label("Gene count:"));
//
//         genecount = new TextField("256", 4);
//         genecount.setName("Gene Count");
//         genecount.addTextListener(this);
//         optpan.add(genecount);
//
//         prevcount = genecount.getText();
//
//         Button clos = new Button("Close");
//         clos.addActionListener(this);
//         clos.setActionCommand("Close");
//         optpan.add(clos);
//
//         mainpan.add(canpan);
//         mainpan.add(buttpanpan);
//
//         canvas = new MyGLCanvas();
//         canvas.setSize(
//             new Dimension(
//                 CANVAS_WIDTH, CANVAS_HEIGHT
//             )
//         );
//         canpan.add(canvas);
//
//         //final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);
//
//         open = new Button("Open");
//         open.addActionListener(this);
//         open.setActionCommand("Open");
//         buttpan.add(open);
//         //butt1.setPreferredSize(new Dimension(85,25));
//
//         strt = new Button("Start");
//         strt.addActionListener(this);
//         strt.setActionCommand("Start");
//         strt.setEnabled(false);
//         buttpan.add(strt);
//
//         paus = new Button("Pause");
//         paus.addActionListener(this);
//         paus.setActionCommand("Pause");
//         paus.setEnabled(false);
//         buttpan.add(paus);
//
//         stop = new Button("Stop");
//         stop.addActionListener(this);
//         stop.setActionCommand("Stop");
//         stop.setEnabled(false);
//         buttpan.add(stop);
//
//         /*butt2 = new Button("Repaint");
//         butt2.addActionListener(this);
//         butt2.setActionCommand("Repaint");
//         butt2.setEnabled(false);*/
//         //butt1.setPreferredSize(new Dimension(85,25));
//
//         Button save = new Button("Save");
//         save.addActionListener(this);
//         save.setActionCommand("Save");
//         buttpan.add(save);
//         //butt.setEnabled(false);
//         //butt2.setPreferredSize(new Dimension(85,25));
//
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
        Panel optpan = createGridPanel(3, 2, 10, 10);

        optpan.add(
            createButton("CLOSE", frame, CLOSE_OPTIONS.toString()));

        frame.add(optpan, OPTION_PANEL.toString());
    }

    private void buildMainPanel(App frame) {
        Panel mainpan = createFlowPanel();
        mainpan.setBackground(Color.WHITE);

        mainpan.add(
            createCanvasPanel(frame));

        mainpan.add(
            createButtonPanel(frame));

        frame.add(mainpan, MAIN_PANEL.toString());
    }

    private Panel createCanvasPanel(App frame) {
        Panel canPan = createFlowPanel();
//        Dimension dim = frame.getPreferredSize();

//        canPan.setPreferredSize(
//            new Dimension(
//                (int) (dim.getWidth() * 0.85),
//                (int) (dim.getHeight() * 0.9)));

        return canPan;
    }

    private Panel createButtonPanel(App frame) {
        Panel buttPan = createFlowPanel();

        Panel centerPan = createGridPanel(2, 1, 0, 10);

        Panel operationButtonPan = createFlowPanel();
        operationButtonPan.setBackground(Color.WHITE);

        Panel optionButtonPan = createFlowPanel();
        optionButtonPan.setBackground(Color.WHITE);

        centerPan.add(operationButtonPan);
        centerPan.add(optionButtonPan);

        buttPan.add(centerPan);
//        Dimension dim = frame.getPreferredSize();

//        buttPan.setPreferredSize(
//            new Dimension(
//                (int) (dim.getWidth() * 0.10),
//                (int) (dim.getHeight() * 0.9)));

        operationButtonPan.add(
            createOperationButtonsPanel(frame));

        optionButtonPan.add(
            createButton("OPTIONS", frame, OPEN_OPTIONS.toString()));

        return buttPan;
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
