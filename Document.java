import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * The Document represents text as an array list of ropes
 */
public class Document {
    /**
     * The file is represented by an array list of ropes
     */
    private ArrayList<Rope> ropes;

    /**
     * Create a document represented by an array list of ropes
     */
    public Document() {
        // TODO - your code here
        this.ropes = new ArrayList<>();
    }

    /**
     * Read a file into the document
     */
    public boolean read(InputStream is) {
        // TODO - your code here
        ropes.clear();
        try {
            StringBuilder sb = new StringBuilder();
            Scanner sc = new Scanner(is);  
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine()).append('\n');
            }
            sc.close();
            String[] lines = sb.toString().split("\n", -1);
            for (String line : lines) {
                ropes.add(makeWordRope(line));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Create a rope made up of words from a string by concatenating
     * words from the string. Return null on an empty string.
     * E.g a string "Fee fi" will result in a rope like this:
     *                    +
     *               ------------
     *               |           |
     *            -------       fi
     *            |     |
     *           Fee   (whitespace)
     *
     */
    public Rope makeWordRope(String line) {
        // TODO - your code here
        if (line == null || line.isEmpty()) { 
            return null;
        }

        Rope result = null;
        int n = line.length();
        int i = 0;

        while (i < n) {
            char c = line.charAt(i);
            boolean isSpace = Character.isWhitespace(c);

            // find the next token (word or whitespace)
            int j = i + 1;
            while (j < n && Character.isWhitespace(line.charAt(j)) == isSpace) {
                j++;
            }

            String token = line.substring(i, j);
            if (!token.isEmpty()) {
                Rope piece = new Rope(token);

                if (result == null) {
                    result = piece;
                } else {
                    result = result.concat(piece);   // concatenate the new piece
                }
            }
            i = j;
        }
        return result;
    }

    /**
     * Helpful function to print a document
     */
    public void print() {
        for (int i = 0; i < ropes.size(); i++) {
            String line = "";
            Rope rope = ropes.get(i);
            if (rope != null) {
                line = rope.collect();
            }
            System.out.println((i + 1) + ":" + line);
        }
    }

    /**
     * Output the document to a file
     */
    public boolean write(OutputStream os) {
        // TODO - your code here
        try {
            PrintStream ps = new PrintStream(os);

            for (int i = 0; i < ropes.size(); i++) {
                Rope rope = ropes.get(i);
                if (rope != null) {
                    ps.print(rope.collect());
                }
                // print newline except for last line
                if (i < ropes.size() - 1) {
                    ps.println();
                }
            }
            ps.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Collect the document as one big string
     */
    public String collect() {
        // TODO - your code here
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ropes.size(); i++) {
            Rope rope = ropes.get(i);
            if (rope != null) {
                sb.append(rope.collect());
            }
            if (i < ropes.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Get the total number of lines
     */
    public int rows() {
        // TODO - your code here
        return ropes.size();
    }

    /**
     * Get the rope at line i
     */
    public Rope get(int i) {
        // TODO - your code here
        return ropes.get(i);
    }

    /**
     * Set the rope at line i
     */
    public void set(int i, Rope rope) {
        // TODO - your code here\
        ropes.set(i, rope);
    }

    /**
     * Grow the ropes by 1
     */
    public void add(Rope rope) {
        // TODO - your code here
        ropes.add(rope);
    }

    /**
     * Insert a rope at line i
     */
    public void add(int i, Rope rope) {
        // TODO - your code here
        ropes.add(i, rope);
    }

    /**
     * Delete row i in the document
     */
    public void delete(int i) {
        // TODO - your code here
        ropes.remove(i);
    }

    
}
