package cj.studio.netos.framework.netarea;

import cj.studio.netos.framework.INetAreaWorkspace;

/**
 * Created by caroceanjofers on 2018/2/25.
 */

public class NetAreaWorkspace implements INetAreaWorkspace {
    private NetAreaWorkspace(){

    }
    public static INetAreaWorkspace open(String url) {
        NetAreaWorkspace workspace=new NetAreaWorkspace();
        workspace.load(url);
        return workspace;
    }

    private void load(String url) {

    }
}
