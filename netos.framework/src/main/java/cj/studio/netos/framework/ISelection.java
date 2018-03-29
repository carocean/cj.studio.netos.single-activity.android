package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/2/6.
 */

public interface ISelection {
    void onselected(IModule selected);

    IModule selected();
}
