import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Editor {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("No file argument");
            return;
        }

        Document document = new Document();
        FileInputStream fis = new FileInputStream(args[0]);
        document.read(fis);
        Gui gui = new Gui();

        // This try block is used to catch any exceptions inside
        try {
            View view = new View(document, gui);
            gui.start();

            for (boolean quit = false; !quit;) {
                gui.clear();
                view.draw();
                gui.refresh();

                view.draw();

                int c = 0;
                while ((c = gui.getKeypress()) == 0) {
                    gui.sleep(10);
                }
                switch (c) {
                case Gui.ESCAPE_KEY:
                    quit = true;
                    break;

                case Gui.UP_ARROW_KEY:
                    view.moveUp();
                    break;

                case Gui.DOWN_ARROW_KEY:
                    view.moveDown();
                    break;

                case Gui.LEFT_ARROW_KEY:
                    view.moveLeft();
                    break;

                case Gui.RIGHT_ARROW_KEY:
                    view.moveRight();
                    break;

                case Gui.BACKSPACE_KEY:
                    view.delete();
                    break;

                case Gui.ENTER_KEY:
                    view.linebreak();
                    break;

                default:
                    if (gui.isPrintable(c)) {
                        view.insert((char) c);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            // An exception occurred. Stop the GUI and print the message, then exit
            // with an error code. We need to stop the GUI first so that
            // the error can be printed out correctly.
            gui.stop();
            System.out.println("\n" + e);
            e.printStackTrace();
            System.exit(1);
        }

        // Clean exit -- stop the GUI
        gui.stop();
    }
}
