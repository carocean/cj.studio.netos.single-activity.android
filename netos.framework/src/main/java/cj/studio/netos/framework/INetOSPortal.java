package cj.studio.netos.framework;

import java.util.List;

/**
 * Created by caroceanjofers on 2018/2/23.
 */

public interface INetOSPortal {
    public IContainer container();
    public ITitlebar titlebar();
    ISelection selection();
    ISlideButton slideButton();
    List<IModule> modules();
    public INavigation navigation();

    IHistory history();

    IDownSlideRegion downSlideRegion();

    INetAreaWorkbench netArea();

    INetOSResource resource();
}
