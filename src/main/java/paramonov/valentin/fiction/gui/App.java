package paramonov.valentin.fiction.gui;

import paramonov.valentin.fiction.gui.action.Action;
import paramonov.valentin.fiction.gui.builder.AppGUIBuilder;

import java.awt.*;
import java.awt.event.*;

import static paramonov.valentin.fiction.gui.builder.PanelEnum.MAIN_PANEL;
import static paramonov.valentin.fiction.gui.builder.PanelEnum.OPTION_PANEL;

public class App
extends Frame
implements ActionListener, WindowListener, ItemListener, TextListener {
    private static final long serialVersionUID = 0xfade;
    private static String TITLE = "FICtion";
    private static final int CANVAS_WIDTH = 768;
    private static final int CANVAS_HEIGHT = 432;
    
    //private static final int MINCOUNT = 0x10;
    //private static final int MAXCOUNT = 0x1000;
    //private static final int FPS = 60;

    //private MyGLCanvas canvas;
    private FileDialog fds;
    private FileDialog fdo;
    private Button open;
    private Button strt;
    private Button paus;
    private Button stop;
    private Button opts;
    private Choice popsize;
    private TextField genecount;
    

    private String prevcount;
   // private int popsize = 16;

    public App() {
        super(TITLE);
//        this.setResizable(false);
        this.addWindowListener(this);

        buildGUI();
        //animator.start();
    }

    public void buildGUI() {
        new AppGUIBuilder().buildGUI(this);
    }

    public void start() {
        this.setPreferredSize(new Dimension(900,450));
        this.pack();
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String comm = ae.getActionCommand();
        Action act;

        try {
            act = Action.valueOf(comm);
        } catch(IllegalArgumentException iae) {
            System.out.println("No such action: " + comm);
            return;
        }

        switch(act) {
//            case "Repaint":
//                //canvas.drawToScreen();
//                //canvas.displayImage();
//                //((MyGLCanvas) this.getComponent(0)).drawToScreen();
//                //((MyGLCanvas) this.getComponent(0)).displayImage();
//                break;
//
//            case "Save":
//                fds.setVisible(true);
//
//                if(null == fds.getFile()) {
//                    break;
//                }
//
//                canvas.takeSnapshot(
//                    fds.getDirectory() + fds.getFile()
//                );
//
//                break;
//
//            case "Open":
//                fdo.setVisible(true);
//
//                if(null == fdo.getFile()) {
//                    break;
//                }
//
//                canvas.loadImage(
//                    fdo.getDirectory() + fdo.getFile());
//
//                //canvas.createFBO();
//
//                //canvas.displayImage();
//
//                //butt2.setEnabled(true);
//                strt.setEnabled(true);
//                //butt4.setEnabled(true);
//
//                break;
//
//            case "Start":
//                ptype= PrimitiveTypes.PT_CIRCLE;
//
//                switch(ptype) {
//                    case PT_CIRCLE:
//                        rtn = new Routine<Circle>(
//                            canvas,
//                            Circle.class,
//                            new double[]{
//                                Double.parseDouble(popsize.getSelectedItem()),
//                                Double.parseDouble(genecount.getText())});
//                            break;
//
//                    default:
//                        return;
//                }
//
//                open.setEnabled(false);
//                paus.setEnabled(true);
//                stop.setEnabled(true);
//                strt.setEnabled(false);
//                opts.setEnabled(false);
//
//                break;
//
//            case "Pause":
//                rtn.suspend();
//
//                paus.setLabel("Resume");
//                paus.setActionCommand("Resume");
//
//                break;
//
//            case "Resume":
//                rtn.resume();
//
//                paus.setLabel("Pause");
//                paus.setActionCommand("Pause");
//
//                break;
//
//            case "Stop":
//                rtn.stop();
//                //rtn.join();
//
//                open.setEnabled(true);
//                strt.setEnabled(true);
//                opts.setEnabled(true);
//                paus.setEnabled(false);
//                stop.setEnabled(false);
//
//                paus.setLabel("Pause");
//                paus.setActionCommand("Pause");
//
//                break;

            case OPEN_OPTIONS:
                switchToOptionPane();
                break;

            case CLOSE_OPTIONS:
                switchFromOptionPane();
                break;
        }
    }

    private void switchToOptionPane() {
        CardLayout cl = (CardLayout) this.getLayout();

        cl.show(this, OPTION_PANEL.toString());

        this.setTitle(TITLE + ": Options");
    }

    private void switchFromOptionPane() {
        CardLayout cl = (CardLayout) this.getLayout();

        cl.show(this, MAIN_PANEL.toString());

        this.setTitle(TITLE);
    }

    public void itemStateChanged(ItemEvent ie) {
//         switch(
//             ((Component) ie.getSource()).getName()) {
//             
//             case "Population Size":
//                 //popsize = Integer.decode(
//                  //   ie.getItem().toString());
//                 break;
//         }
//         //System.out.println(((Component) ie.getSource()).getName());
//         //System.out.println(ie.getItem());
    }
    
    public void textValueChanged(TextEvent te) {
//         //System.out.println(te.paramString());
//         switch(((Component) te.getSource()).getName()) {
//             case "Gene Count":
//                 int newcount;
//             
//                 try {
//                     newcount = Integer.parseInt(genecount.getText());
//                 }
//                 catch(NumberFormatException nfe) {
//                     genecount.setText(prevcount);
//                     return;
//                 }
//                 
//                 //genecount.removeTextListener(this);
//                 
//                 if(newcount < MINCOUNT) {
//                     genecount.setText(Integer.toString(MINCOUNT));
//                 }
//                 
//                 if(newcount > MAXCOUNT) {
//                     genecount.setText(Integer.toString(MAXCOUNT));
//                 }
//                 
//                 //genecount.setText(Integer.toString(newcount));
//                 prevcount = genecount.getText();
//                 
//                 //genecount.addTextListener(this);
//                 //System.out.println(genecount.getText());
//                 break;
//         }
//         //System.out.println(te.paramString());
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
//        new Thread() {
//            public void run() {
//                    if (rtn != null) {
//                        rtn.stop();
//
//                        //try {
//                        rtn.join();
//                        //}
//                        //catch(InterruptedException ie) {
//                        //    ie.printStackTrace();
//                        //}
//                    }
                //if(animator.isStarted()) animator.stop();
                System.exit(0);
//            }
//        }.start();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
