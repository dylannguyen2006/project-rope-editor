import java.util.HashMap;

public final class Rope {
    /**
     * These instance variables are declared final to make the Rope
     * data structure immutable, ie. they can't be changed after they've
     * been assigned once in the constructor.
     */
    public final String data;
    public final Rope left;
    public final Rope right;
    public final int weight;

    /**
     * Create leaf Rope from string data
     * Data cannot be null and cannot be the empty string
     */
    public Rope(String data) {
        // TODO - your code here
        // replace these initializers
        this.data = null;
        this.left = null;
        this.right = null;
        this.weight = 0;
    }

    /**
     * Create Rope from left and right ropes.
     * The Rope weight is the weight of the left rope.
     * Left and right ropes cannot be null.
     */
    public Rope(Rope left, Rope right) {
        // TODO - your code here
        // replace these initializers
        this.data = null;
        this.left = null;
        this.right = null;
        this.weight = 0;
    }

    /**
     * Print the rope out. Useful for debugging
     */
    public String toString() {
        String leftString = "";
        if (left != null) {
            leftString = left.toString();
        }
        String rightString = "";
        if (right != null) {
            rightString = right.toString();
        }
        return "(" + weight + "," + data + ",(" + leftString + "),"
            + "(" + rightString + ")";
    }

    /**
     * Return the total weight of the rope from
     * both the left and right ropes
     */
    public int totalWeight() {
        // TODO - your code here
        return 0;
    }

    /**
     * Concatenate this rope with another by creating a parent
     * rope node and adding this and other as children.
     * Concatenating a null rope has no effect.
     */
    public Rope concat(Rope other) {
        // TODO - your code here
        return null;
    }

    /**
     * Add a word by creating a rope and concatenating with this rope.
     * This is a convenience wrapper over the concat function.
     */
    public Rope add(String data) {
        // TODO - your code here
        return null;
    }

    /**
     * Return true if this node is a leaf node, false otherwise
     */
    public boolean isLeaf() {
        // TODO - your code here
        return false;
    }

    /**
     * Find the char at a particular node
     */
    public char charAt(int i) {
        // TODO - your code here
        return 'x';
    }

    /**
     * Return this node as a string by accumulating the data
     * from left and right ropes.
     */
    public String collect() {
        // TODO - your code here
        return "";
    }

    /**
     * Return the tail of this rope at index i to the end of the rope.
     * E.g If the rope is "firefly", then the tail of the rope at
     * index 2 would return the rope "refly"
     */
    public Rope tail(int i) {
        // TODO - your code here
        return null;
    }

    /**
     * head of rope from index 0 up to but not including i.
     * E.g. If the rope is "firefly", then head of the rope at
     * index 2 would return the rope "fi"
     */
    public Rope head(int i) {
        // TODO - your code here
        return null;
    }

    /**
     * Return the sub rope from index start up to but not including end.
     * This is a wrapper around the head and tail functions.
     */
    public Rope subrope(int start, int end) {
        // TODO - your code here
        return null;
    }

    /**
     * Delete a character from this rope at index i.
     * E.g If the rope is "firefly", then deleting the rope at
     * index 2 would return the rope "fiefly".
     * Deleting can result in a null rope if there is only one character
     * and it gets deleted.
     */
    public Rope delete(int i) {
        // TODO - your code here
        return null;
    }

    /**
     * Insert a rope at index i
     */
    public Rope insert(Rope other, int i) {
        // TODO - your code here
        return null;
    }

    /**
     * Reduce the rope to its most space efficient form
     */
    public Rope reduce() {
        // TODO - your code here
        return null;
    }
}
