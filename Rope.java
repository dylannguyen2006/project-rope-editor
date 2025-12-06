import java.util.HashMap;

public final class Rope {
    // For leaves: data != null, left == right == null, weight = data.length()
    // For internal nodes: data == null, left/right != null, weight = totalWeight(left)
    public final String data;
    public final Rope left;
    public final Rope right;
    public final int weight;

    /**
     * Create leaf Rope from string data
     * Data cannot be null or empty
     */
    public Rope(String data) {
        if (data == null || data.length() == 0) {
            throw new IllegalArgumentException("Data cannot be null or empty");
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
     * Format: (weight data left right)
     */
    @Override
    public String toString() {
        String d = (data == null ? "null" : data);
        String l = (left == null ? "null" : left.toString());
        String r = (right == null ? "null" : right.toString());
        return "(" + weight + " " + d + " " + l + " " + r + ")";
    }

    /**
     * Return the total weight of the rope from
     * both the left and right ropes
     */
    public int totalWeight() {
        if (isLeaf()) {
            return weight;
        }
        return weight + right.totalWeight();
    }

    /**
     * Return true if this node is a leaf node, false otherwise
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Concatenate this rope with another by creating a parent
     * rope node and adding this and other as children.
     * Concatenating a null rope has no effect.
     */
    public Rope concat(Rope other) {
        if (other == null) {
            return this;
        }
        return new Rope(this, other);
    }

    /**
     * Add a word by creating a rope and concatenating with this rope.
     * This is a convenience wrapper over the concat function.
     */
    public Rope add(String data) {
        if (data == null || data.length() == 0) {
            return this;
        }
        Rope leaf = new Rope(data);
        return this.concat(leaf);
    }

    /**
     * Find the char at a particular node
     */
    public char charAt(int i) {
        int total = totalWeight();
        if (i < 0 || i >= total) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (isLeaf()) {
            return data.charAt(i);
        }
        if (i < weight) {
            return left.charAt(i);
        } else {
            return right.charAt(i - weight);
        }
    }

    /**
     * Return this node as a string by accumulating the data
     * from left and right ropes.
     */
    public String collect() {
        if (isLeaf()) {
            return data;
        }
        return left.collect() + right.collect();
    }

    /**
     * Return the tail of this rope at index i to the end of the rope.
     * E.g If the rope is "firefly", then the tail of the rope at
     * index 2 would return the rope "refly"
     */
    public Rope tail(int i) {
        int total = totalWeight();
        if (i < 0 || i > total) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (i == 0) {
            // must return the exact same object (tests use ==)
            return this;
        }
        if (i == total) {
            return null;
        }

        if (isLeaf()) {
            String sub = data.substring(i);
            if (sub.length() == 0) {
                return null;
            }
            return new Rope(sub);
        }

        if (i < weight) {
            Rope newLeft = left.tail(i);
            if (newLeft == null) {
                return right;           // share right
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
            if (sub.length() == 0) {
                return null;
            }
            return new Rope(sub);
        }

        if (i < weight) {
            return left.head(i);
        } else if (i == weight) {
            // can reuse the whole left subtree
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
        int total = totalWeight();
        if (start < 0 || end < start || end > total) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (start == end) {
            return null;
        }
        Rope t = tail(start);
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
        int total = totalWeight();
        if (i < 0 || i >= total) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (isLeaf()) {
            String newData = data.substring(0, i) + data.substring(i + 1);
            if (newData.length() == 0) {
                return null;
            }
            return new Rope(newData);
        }

        if (i < weight) {
            Rope newLeft = left.delete(i);
            if (newLeft == null) {
                return right;           // share right
            }
            return new Rope(newLeft, right);
        } else {
            Rope newRight = right.delete(i - weight);
            if (newRight == null) {
                return left;            // share left
            }
            return new Rope(left, newRight);
        }
    }

    /**
     * Insert a rope at index i
     */
    public Rope insert(Rope other, int i) {
        int total = totalWeight();
        if (i < 0 || i > total) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (other == null) {
            return this;
        }

        Rope leftPart = head(i);
        Rope rightPart = tail(i);

        Rope result;
        if (leftPart == null) {
            result = other;
        } else {
            result = leftPart.concat(other);
        }

        if (rightPart != null) {
            result = result.concat(rightPart);
        }

        return result;
    }

    /**
     * Reduce the rope to its most space efficient form
     */
    public Rope reduce() {
        return reduceString(this, new HashMap<String, Rope>());
    }

    // helper function for reduce
    private static Rope reduceString(Rope node, HashMap<String, Rope> map) {
        if (node == null) {
            return null;
        }
        if (node.isLeaf()) {
            Rope exist = map.get(node.data);
            if (exist != null) {
                return exist;
            } else {
                map.put(node.data, node);
                return node;
            }
        }
        Rope newLeft = reduceString(node.left, map);
        Rope newRight = reduceString(node.right, map);
        return new Rope(newLeft, newRight);
    }
}
