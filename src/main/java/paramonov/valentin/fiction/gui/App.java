package paramonov.valentin.fiction.gui;

import paramonov.valentin.fiction.gui.builder.AppGUIBuilder;
import paramonov.valentin.fiction.gui.builder.GUIBuilder;

import java.awt.*;
import java.awt.event.*;

/*
import java.awt.color.ColorSpace;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
*/

import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.FilenameFilter;

import java.nio.ByteBuffer;

import javax.media.opengl.awt.GLCanvas;
import javax.imageio.ImageIO;

//import com.jogamp.opengl.util.FPSAnimator;

public class App
extends Frame
implements ActionListener, ItemListener, TextListener {
    private static final long serialVersionUID = 0xfade;
    private static String TITLE = "FICtion";
    private static final int CANVAS_WIDTH = 768;
    private static final int CANVAS_HEIGHT = 432;
    private static final String MAIN_PANEL = "0";
    private static final String OPTION_PANEL = "1";
    
    //private static final int MINCOUNT = 0x10;
    //private static final int MAXCOUNT = 0x1000;
    //private static final int FPS = 60;

    //private MyGLCanvas canvas;
    private GUIBuilder guiBuilder;
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

        guiBuilder = new AppGUIBuilder();
          
        Panel mainpan = new Panel(new FlowLayout());
        Panel optpan = new Panel(new GridLayout(3,2,10,10));
        Panel canpan = new Panel(new FlowLayout());
        Panel buttpanpan = new Panel(new FlowLayout());
        Panel buttpan = new Panel(new GridLayout(5,1,0,0));

        this.setPreferredSize(new Dimension(900,450));
        //this.setLayout(new FlowLayout());
        this.setLayout(new CardLayout());
        this.setResizable(false);
        this.setTitle(TITLE);

//         this.add(mainpan, MAIN_PANEL);
//         this.add(optpan, OPTION_PANEL);
        //this.add(canpan);
        //this.add(buttpanpan);
        //this.add(buttpan);

//         canpan.setPreferredSize(new Dimension(778, 450));
//         buttpanpan.setPreferredSize(new Dimension(100, 120));
//         
//         buttpan.setPreferredSize(new Dimension(85,110));
//         buttpanpan.add(buttpan);
        
        this.add(guiBuilder.addButton("OK", this, "OK"));

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
// 
//         this.addWindowListener(new WindowAdapter() {
//             public void windowClosing(WindowEvent e) {
//                 new Thread() {
//                     public void run() {
//                         if(rtn != null) {
//                             rtn.stop();
// 
//                             //try {
//                                 rtn.join();
//                             //}
//                             //catch(InterruptedException ie) {
//                             //    ie.printStackTrace();
//                             //}
//                         }
//                         //if(animator.isStarted()) animator.stop();
//                         System.exit(0);
//                     }
//                 }.start();
//             }
//         });

        this.pack();
        this.setVisible(true);
        
        //animator.start();
    }

    public void start() {

    }

    public void actionPerformed(ActionEvent ae) {
        /*switch(ae.getActionCommand()) {
            case "Repaint":
                //canvas.drawToScreen();
                //canvas.displayImage();
                //((MyGLCanvas) this.getComponent(0)).drawToScreen();
                //((MyGLCanvas) this.getComponent(0)).displayImage();
                break;

            case "Save":
                fds.setVisible(true);

                if(null == fds.getFile()) {
                    break;
                }

                canvas.takeSnapshot(
                    fds.getDirectory() + fds.getFile()
                );

                break;

            case "Open":
                fdo.setVisible(true);

                if(null == fdo.getFile()) {
                    break;
                }

                canvas.loadImage(
                    fdo.getDirectory() + fdo.getFile());

                //canvas.createFBO();

                //canvas.displayImage();

                //butt2.setEnabled(true);
                strt.setEnabled(true);
                //butt4.setEnabled(true);

                break;

            case "Start":
                ptype= PrimitiveTypes.PT_CIRCLE;

                switch(ptype) {
                    case PT_CIRCLE:
                        rtn = new Routine<Circle>(
                            canvas,
                            Circle.class,
                            new double[]{
                                Double.parseDouble(popsize.getSelectedItem()),
                                Double.parseDouble(genecount.getText())});
                            break;
                
                    default:
                        return;
                }

                open.setEnabled(false);
                paus.setEnabled(true);
                stop.setEnabled(true);
                strt.setEnabled(false);
                opts.setEnabled(false);

                break;

            case "Pause":
                rtn.suspend();

                paus.setLabel("Resume");
                paus.setActionCommand("Resume");

                break;

            case "Resume":
                rtn.resume();

                paus.setLabel("Pause");
                paus.setActionCommand("Pause");

                break;

            case "Stop":
                rtn.stop();
                //rtn.join();

                open.setEnabled(true);
                strt.setEnabled(true);
                opts.setEnabled(true);
                paus.setEnabled(false);
                stop.setEnabled(false);
                
                paus.setLabel("Pause");
                paus.setActionCommand("Pause");

                break;

            case "Options":
                ((CardLayout)this.getLayout()).show(this, OPTION_PANEL);
                break;

            case "Close":
                ((CardLayout)this.getLayout()).show(this, MAIN_PANEL);
                break;
        }*/
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
}
