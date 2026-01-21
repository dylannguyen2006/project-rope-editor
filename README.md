# Rope Editor

Rope Editor is a terminal-based text editor implemented in Java using the
**rope data structure** to efficiently support text insertion and deletion.
This project demonstrates how classical data structures are used in real-world
applications such as text editors.

The editor follows the **Model–View–Controller (MVC)** architecture and renders
a text-based user interface using the Lanterna library.

---

## 🧠 Background: Rope Data Structure

A **rope** is a binary tree representation of a string that allows efficient
operations in the middle of large texts.

Instead of storing text as a contiguous array of characters (which makes
insertions and deletions expensive), a rope:
- Stores substrings in **leaf nodes**
- Uses **internal nodes** to track subtree weights
- Supports logarithmic-time operations for:
  - character lookup
  - insertion
  - deletion
  - concatenation

Ropes are **immutable** in this project: every modification returns a new rope,
making reasoning and testing simpler.

---

## ✨ Features

### Rope Operations
- Collect full string from a rope
- Compute total weight
- Character lookup (`charAt`)
- Concatenation
- Head / Tail / Subrope
- Insert and delete at arbitrary positions
- Rope reduction with node sharing (hash-based deduplication)

### Document Editing
- Each line of the document is stored as a rope
- Supports:
  - reading from file
  - writing to file
  - inserting and deleting text
  - adding and removing lines

### Text Editor
- Terminal-based UI
- Cursor navigation (arrow keys)
- Text insertion and deletion
- Line splitting and joining
- File saved on exit

---

## 🏗️ Architecture (MVC)

### Model
- **`Rope`**: immutable rope tree representing text
- **`Document`**: list of ropes, one per line

### View
- **`View`**: renders the visible portion of the document
- Handles cursor position and screen boundaries

### Controller
- **`Editor`**: main control loop
- Processes user keystrokes
- Coordinates updates between the model and the view

---

## 🗂️ Project Structure

