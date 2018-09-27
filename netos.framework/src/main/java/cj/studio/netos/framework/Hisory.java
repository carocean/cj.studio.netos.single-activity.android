package cj.studio.netos.framework;

import java.util.Stack;

class Hisory implements IHistory {
    ILocation location;
    Stack<Memento> stack;

    public Hisory(ILocation location) {
        stack = new Stack<>();
        this.location = location;
    }
    @Override
    public void clear(){
         stack.clear();
    }
    @Override
    public void redo(Memento memento) {
        stack.push(memento);
    }

    @Override
    public boolean undo() {//true表示已无历史，告知系统可退出app了
        if (stack.size() < 1) {
            return true;
        }
        Memento memento = stack.pop();
        location.locate(memento.path);
        return false;
    }
}
