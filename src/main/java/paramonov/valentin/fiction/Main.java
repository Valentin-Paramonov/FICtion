package paramonov.valentin.fiction;

import paramonov.valentin.fiction.gui.App;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        final App app = new App();
        final Runnable runApp = new Runnable() {
            @Override
            public void run() {
                app.launch();
            }
        };

        System.out.println(
            "You're running " +
                System.getProperty("os.name") +
                " (" +
                System.getProperty("os.arch") +
                ")");

        EventQueue.invokeLater(runApp);
    }
}
