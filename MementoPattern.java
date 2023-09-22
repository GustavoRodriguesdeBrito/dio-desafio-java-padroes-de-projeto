import java.util.ArrayList;
import java.util.List;

// Originator: Represents the text editor whose state we want to save and restore.
class TextEditor {
    private String content;

    public TextEditor() {
        this.content = "";
    }

    public void appendText(String text) {
        content += text;
    }

    public String getContent() {
        return content;
    }

    public void restoreFromMemento(TextEditorMemento memento) {
        content = memento.getSavedContent();
    }

    public TextEditorMemento saveToMemento() {
        return new TextEditorMemento(content);
    }
}

// Memento: Represents a snapshot of the text editor's state.
class TextEditorMemento {
    private final String savedContent;

    public TextEditorMemento(String content) {
        this.savedContent = content;
    }

    public String getSavedContent() {
        return savedContent;
    }
}

// Caretaker: Manages the history of the text editor's state using mementos.
class TextEditorHistory {
    private final List<TextEditorMemento> history = new ArrayList<>();
    private int currentState = -1;

    public void save(TextEditor textEditor) {
        TextEditorMemento memento = textEditor.saveToMemento();
        history.add(memento);
        currentState = history.size() - 1;
    }

    public boolean canUndo() {
        return currentState > 0;
    }

    public void undo(TextEditor textEditor) {
        if (canUndo()) {
            currentState--;
            TextEditorMemento memento = history.get(currentState);
            textEditor.restoreFromMemento(memento);
        }
    }

    public boolean canRedo() {
        return currentState < history.size() - 1;
    }

    public void redo(TextEditor textEditor) {
        if (canRedo()) {
            currentState++;
            TextEditorMemento memento = history.get(currentState);
            textEditor.restoreFromMemento(memento);
        }
    }
}

public class MementoPattern {
    public static void main(String[] args) {
        TextEditor textEditor = new TextEditor();
        TextEditorHistory history = new TextEditorHistory();

        textEditor.appendText("Hello, ");
        history.save(textEditor);

        textEditor.appendText("world!");
        history.save(textEditor);

        System.out.println("Current content: " + textEditor.getContent());

        history.undo(textEditor);
        System.out.println("Undo: " + textEditor.getContent());

        history.redo(textEditor);
        System.out.println("Redo: " + textEditor.getContent());
    }
}
