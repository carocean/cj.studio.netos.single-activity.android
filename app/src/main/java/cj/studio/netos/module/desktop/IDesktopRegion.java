package cj.studio.netos.module.desktop;

import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.IWindow;

public interface IDesktopRegion extends IWindow {
    void input(Frame frame, ICell cell);
}
