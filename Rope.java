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
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        if (data.length() == 0) {
            throw new IllegalArgumentException("Data cannot be empty string");
        }
        this.data = data;
        this.left = null;
        this.right = null;
        this.weight = data.length();
    }

    /**
     * Create Rope from left and right ropes.
     * The Rope weight is the weight of the left rope.
     * Left and right ropes cannot be null.
     */
    public Rope(Rope left, Rope right) {
        // TODO - your code here
        if (left == null || right == null) {
            throw new IllegalArgumentException("Left and right ropes cannot be null");
        }
        this.data = null;
        this.left = left;
        this.right = right;
        this.weight = left.totalWeight();
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
        if (isLeaf()) {   
            return weight;
        }
        int rightWeight;
        if (right == null) {
            rightWeight = 0;
        } else {
            rightWeight = right.totalWeight();  
        }
        return weight + rightWeight;   // left weight + right weight ( weight is left weight)
    }

    /**
     * Concatenate this rope with another by creating a parent
     * rope node and adding this and other as children.
     * Concatenating a null rope has no effect.
     */
    public Rope concat(Rope other) {
        // TODO - your code here
        if (other == null) {   
            return this;
        }
        return new Rope(this, other);   // create new parent rope
    }

    /**
     * Add a word by creating a rope and concatenating with this rope.
     * This is a convenience wrapper over the concat function.
     */
    public Rope add(String data) {
        // TODO - your code here
        if (data == null || data.length() == 0) {  // empty string
            return this;
        }
        Rope leaf = new Rope(data);
        return this.concat(leaf);
    }

    /**
     * Return true if this node is a leaf node, false otherwise
     */
    public boolean isLeaf() {
        // TODO - your code here
        return left == null && right == null;
    }

    /**
     * Find the char at a particular node
     */
    public char charAt(int i) {
        // TODO - your code here
        int totalWeight = totalWeight();
        if (i < 0 || i >= totalWeight) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (isLeaf()) {
            return data.charAt(i);   // return the char in leaf node
        }
        if (i < weight) {
            return left.charAt(i);   // get char from left rope
        } else {
            return right.charAt(i - weight); 
            // get char from right rope.
            //  i - weight to adjust index
        }
    }

    /**
     * Return this node as a string by accumulating the data
     * from left and right ropes.
     */
    public String collect() {
        // TODO - your code here
        if (isLeaf()) {
            return data;
        }
        String leftStr = "";
        String rightStr = "";

        if (left != null) {
            leftStr = left.collect();
        }
        if (right != null) {
            rightStr = right.collect();
        } 
        return leftStr + rightStr;
    }

    /**
     * Return the tail of this rope at index i to the end of the rope.
     * E.g If the rope is "firefly", then the tail of the rope at
     * index 2 would return the rope "refly"
     */
    public Rope tail(int i) {
        // TODO - your code here
        int totalWeight = totalWeight();
        if (i < 0 || i > totalWeight) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (i == 0) {
            return this;
        }
        if (i == totalWeight) {   // empty substring
            return null;
        }
        if (isLeaf()) {   // if leaf node, return substring from i to end
            String sub = data.substring(i);   
            if (sub.length() == 0) {
                return null;
            }
            return new Rope(sub);  
        }      
        // if i is in left rope, get tail from
        // left rope and concatenate with right rope

        if (i < weight) {   
            Rope newLeft = left.tail(i);
            if (newLeft == null) {
                return right;
            }
            return new Rope(newLeft, right);
        } else if (i == weight) {
            return right;
        } else {
            return right.tail(i - weight);
        }
    }

    /**
     * head of rope from index 0 up to but not including i.
     * E.g. If the rope is "firefly", then head of the rope at
     * index 2 would return the rope "fi"
     */
    public Rope head(int i) {
        // TODO - your code here
        int total = totalWeight();
        if (i < 0 || i > total) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (i == 0) {
            return null;
        }
        if (i == total) {
            return this;
        }
        if (isLeaf()) {
            String sub = data.substring(0, i);
            if (sub.isEmpty()) {
                return null;
            }
            return new Rope(sub);
        }
        if (i < weight) {
            return left.head(i);
        } else if (i == weight) {
            return left;
        } else {
            Rope rightHead = right.head(i - weight);
            if (rightHead == null) {
                return left;
            }
            return new Rope(left, rightHead);
        }
    }

    /**
     * Return the sub rope from index start up to but not including end.
     * This is a wrapper around the head and tail functions.
     */
    public Rope subrope(int start, int end) {
        // TODO - your code here
        int total = totalWeight();
        if (start < 0 || end < start || end > total) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (start == end) {
            return null; //empty substring
        }
        Rope t = this.tail(start);   // get tail from start to end
        if (t == null) {  
            return null;
        }
        return t.head(end - start);
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
        int total = totalWeight();
        if (i < 0 || i >= total) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (isLeaf()) { 
            StringBuilder sb = new StringBuilder(data);
            sb.deleteCharAt(i);   // delete char at index i
            if (sb.length() == 0) {   // return null if empty string
                return null;
            }
            return new Rope(sb.toString());  // create new rope with updated string after delete
        }
        if (i < weight) {  // i < weight, delete from left rope
            Rope newLeft = left.delete(i);
            if (newLeft == null) {
                return right;
            }
            return new Rope(newLeft, right);
        } else {   // i >= weight, delete from right rope
            Rope newRight = right.delete(i - weight);
            if (newRight == null) {
                return left;
            }
            return new Rope(left, newRight);  
        }
    }

    /**
     * Insert a rope at index i
     */
    public Rope insert(Rope other, int i) {
        // TODO - your code here
        int total = totalWeight();
        if (i < 0 || i > total) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (other == null) {
            return this;
        }
        Rope leftSide = head(i);
        Rope rightSide = tail(i);

        Rope result = null;
        if (leftSide != null) {
            result = leftSide;
        }
        if (result == null) {
            result = other;
        } else {
            result = result.concat(other);
        }
        if (rightSide != null) {
            if (result == null) {
                result = rightSide;
            } else {
                result = result.concat(rightSide);
            }
        }
        return result;
    }

    /**
     * Reduce the rope to its most space efficient form
     */
    public Rope reduce() {
        // TODO - your code here
        HashMap<String, Rope> map = new HashMap<>();  
        return reduceString(this, map);
    }

    // helper function for reduce
    private static Rope reduceString(Rope node, HashMap<String, Rope> map) {
        if (node == null) {
            return null;
        }
        if (node.isLeaf()) {
            // using map to store already existing leaf nodes
            Rope exist = map.get(node.data); 
            if (exist != null) {  // if already exists, return
                return exist;
            } else {
                map.put(node.data, node);   // if not, add to map
                return node;
            }
        }
        Rope newLeft = reduceString(node.left, map);   // reduce left subtree
        Rope newRight = reduceString(node.right, map);  // reduce right subtree
        return new Rope(newLeft, newRight);  // create new parent rope
    }
}
