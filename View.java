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
        int screenHeight = gui.getScreenHeight();
        int screenWidth = gui.getScreenWidth();

        int maxRows = Math.min(rows, screenHeight);

        for (int i = 0; i < maxRows; i++) {
            String line;
            Rope rope = document.get(i);

            if (rope == null) {
                line = "";
            } else {
                line = rope.collect();
            }

            int maxCols = Math.min(line.length(), screenWidth);
            for (int c = 0; c < maxCols; c++) {
                char ch = (c < line.length()) ? line.charAt(c) : ' ';
                gui.drawCharacter(c, i, ch, "WHITE", "BLACK");   
                // default colors WHITE on BLACK
            }
        }

        if (rows == 0) {
            row = 0;
            col = 0;
        } else {
            if (row < 0) {
                row = 0;
            } else if (row >= rows) {
                row = rows - 1;
            }

            int len = lineLength(row);
            if (col < 0) {
                col = 0;
            } else if (col > len) { 
                col = len;
            }
        }

        if (row >= screenHeight && screenHeight > 0) {
            row = screenHeight - 1;
        }
        if (col >= screenWidth && screenWidth > 0) {
            col = screenWidth - 1;
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
        int len = lineLength(row);
        if (col < len) {
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
            int len = lineLength(row);
            if (col > len) {
                col = len;
            }
        }
    }

    // move cursor down
    public void moveDown() {
        // TODO - your code here
        if (row < document.rows() - 1) {
            row++;
            int len = lineLength(row);
            if (col > len) {
                col = len;
            }
        }
    }

    // insert a character at this screen position
    public void insert(char c) {
        // TODO - your code here
        if (document.rows() == 0) {
            document.add(new Rope(""));
            row = 0;
            col = 0;
        }
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
        if (document.rows() == 0) {
            document.add(new Rope(""));
        }
        Rope line = document.get(row);
        if (line == null) {
            document.add(row + 1, new Rope(""));
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
            int deleteIndex = col - 1;
            Rope left = line.head(deleteIndex);   // 0 to deleteIndex - 1
            Rope right = line.tail(col);   // col to end

            Rope newLine = null;
            if (left != null && right != null) {
                newLine = left.concat(right);
            } else if (left != null) {
                newLine = left;
            } else if (right != null) {
                newLine = right;
            }
            document.set(row, newLine);
            col--;
            return;
        }

        if (row == 0) {
            return;
        }

        Rope currLine = document.get(row);   // current line
        Rope prevLine = document.get(row - 1);   // previous line
        if (prevLine == null) {
            prevLine = new Rope("");
        } else if (currLine == null) {
            currLine = new Rope("");
        }
        Rope mergedLine = prevLine.concat(currLine);
        int newCol = prevLine.totalWeight();
        
        document.set(row - 1, mergedLine);
        document.delete(row);   // delete current row
        row--;
        col = newCol;    // move cursor to end of previous line
    }
}
