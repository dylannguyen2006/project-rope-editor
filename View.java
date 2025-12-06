/**
 * A View of a Document
 */
public class View {
    /**
     * Create a view
     */
    private Document document;
    private Gui gui;
    private int row;
    private int col;

    public View(Document document, Gui gui) {
        // TODO - your code here
        this.document = document;
        this.gui = gui;
        this.row = 0;
        this.col = 0;
    }

    /**
     * Draw this view with the GUI
     */
    public void draw() {
        // TODO - your code here
        gui.clear();

        int rows = document.rows();
        for (int i = 0; i < rows; i++) {
            Rope rope = document.get(i);
            String line;
            if (rope == null) {
                line = "";
            } else {
                line = rope.collect();
            }

            for (int c = 0; c < line.length(); c++) {
                gui.drawCharacter(i, c, line.charAt(c), "WHITE", "BLACK");   // default colors WHITE on BLACK
            }
        }

        gui.setCursorPosition(col, row);
        gui.refresh();
    }


    private int lineLength(int r) {
        if (r < 0 || r >= document.rows()) {
            return 0;
        }
        Rope rope = document.get(r);
        if (rope == null) {
            return 0;
        }
        return rope.totalWeight();
    }

    // move cursor left
    public void moveLeft() {
        // TODO - your code here
        if (col > 0) {
            col--;
        } else if (row > 0) {
            row--;
            col = lineLength(row);
        }
    }

    // move cursor right
    public void moveRight() {
        // TODO - your code here
        int lineLength = lineLength(row);
        if (col < lineLength) {
            col++;
        } else if (row < document.rows() - 1) {
            row++;
            col = 0;
        }
    }

    // move cursor up
    public void moveUp() {
        // TODO - your code here
        if (row > 0) {
            row--;
            int lineLength = lineLength(row);
            if (col > lineLength) {
                col = lineLength;
            }
        }
    }

    // move cursor down
    public void moveDown() {
        // TODO - your code here
        if (row < document.rows() - 1) {
            row++;
            int lineLength = lineLength(row);
            if (col > lineLength) {
                col = lineLength;
            }
        }
    }

    // insert a character at this screen position
    public void insert(char c) {
        // TODO - your code here
        Rope line = document.get(row);
        Rope left = null;
        Rope right = null;
        if (line != null) {
            left = line.head(col);
            right = line.tail(col);
        }
        Rope mid = new Rope(String.valueOf(c));
        Rope newLine;
        if (left != null) {
            newLine = left.concat(mid);
        } else {
            newLine = mid;
        }
        if (right != null) {
            newLine = newLine.concat(right);
        }
        document.set(row, newLine);
        col++;
    }

    // insert a line break
    public void linebreak() {
        // TODO - your code here
        Rope line = document.get(row);
        if (line == null) {
            document.add(row + 1, null);
            row++;
            col = 0;
            return;
        }
        Rope left = line.head(col);
        Rope right = line.tail(col);
        document.set(row, left);
        document.add(row + 1, right);
        row++;
        col = 0;
    }

    // delete a character at directly before this screen position
    public void delete() {
        // TODO - your code here
        if (document.rows() == 0) {
            return;
        }
        if (col > 0) {
            Rope line = document.get(row);
            if (line == null) {
                return;
            }
            int length = line.totalWeight();
            if (length == 0) {
                return;
            }
            int deleteIndex = col - 1;
            Rope newLine = line.head(deleteIndex);
            
            document.set(row, newLine);
            col--;
            return;
        }

        if (row == 0) {
            return;
        }
        Rope currLine = document.get(row);   // current line
        Rope prevLine = document.get(row - 1);   // previous line
        Rope mergedLine;   // merged line after deletion
        if (prevLine == null) {
            mergedLine = currLine;
        } else if (currLine == null) {
            mergedLine = prevLine;
        } else {
            mergedLine = prevLine.concat(currLine);
        }
        int newCol;
        if (mergedLine == null) {
            newCol = 0;
        } else {
            newCol = mergedLine.totalWeight();   // move cursor to end of previous line
        }
        document.set(row - 1, mergedLine);
        document.delete(row);   // delete current row
        row--;
        col = newCol;    // move cursor to end of previous line
    }
}
