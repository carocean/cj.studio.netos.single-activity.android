package cj.studio.netos.framework;

public interface IHistory {
    public void clear();
    void redo(Memento memento);
    boolean undo();
}
