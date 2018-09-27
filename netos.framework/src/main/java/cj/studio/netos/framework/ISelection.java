package cj.studio.netos.framework;

/**
 * Created by caroceanjofers on 2018/2/6.
 */

public interface ISelection {
    public IViewport getCurrentViewport();
    public void setCurrentViewport(IViewport currentViewport);
    IModule selectedModule();

    void setSelectedModule(IModule selectedModule);

    public IDesktopRegion selectedRegion() ;
    public void setSelectedRegion(IDesktopRegion selectedRegion);

    IWidget selectedWidget();
    void setSelectedWidget(IWidget widget);
}
