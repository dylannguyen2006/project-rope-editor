# Rope Editor

Rope Editor is a terminal-based text editor implemented in Java using the
**rope data structure** to efficiently support text insertion and deletion.
This project demonstrates how classical data structures are applied in
real-world software such as text editors.

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
- Supports efficient operations for:
  - character lookup
  - insertion
  - deletion
  - concatenation

In this project, ropes are **immutable**: each modification returns a new rope,
which simplifies reasoning, testing, and debugging.

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
- Terminal-based user interface
- Cursor navigation
- Text insertion and deletion
- Line splitting and joining
- File saved on exit

---

## 🏗️ Architecture (MVC)

### Model
- **Rope**: immutable rope tree representing text
- **Document**: list of ropes, one per line

### View
- **View**: renders the visible portion of the document
- Handles cursor position and screen boundaries

### Controller
- **Editor**: main control loop
- Processes user keystrokes
- Coordinates updates between the model and the view

---
## 🗂️ Project Structure
.
├── Rope.java # Rope data structure and operations
├── Document.java # File model using ropes
├── View.java # Screen rendering logic
├── Editor.java # Main control loop
├── Gui.java # Terminal drawing wrapper (Lanterna)
├── Test/ # Unit tests
├── Style/ # Style checks
├── Makefile # Build and run targets
└── README.md

## 🗂️ Project Structure

