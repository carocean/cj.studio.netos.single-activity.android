package cj.studio.netos.portal.netarea;

import android.support.v4.app.Fragment;

import cj.studio.netos.framework.Frame;
import cj.studio.netos.framework.ICell;
import cj.studio.netos.framework.INetAreaMutuboard;
import cj.studio.netos.framework.INetAreaWorkspace;

/**
 * Created by caroceanjofers on 2018/2/26.
 */

public class NetAreaMutuboard extends Fragment implements INetAreaMutuboard {
    @Override
    public String name() {
        return null;
    }

    @Override
    public void input(Frame frame, ICell cell) {

    }

    @Override
    public int cnameId() {
        return 0;
    }

    @Override
    public void load(INetAreaWorkspace workspace) {

    }
}
