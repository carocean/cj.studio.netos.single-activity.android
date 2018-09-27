package cj.studio.netos.framework;

public interface ILocation {
    void locate(String path);
    public void setSelection(ISelection selection);
    public void setModuleNav(INavigation moduleNav) ;
    public void setRegionNav(INavigation regionNav);
    public void setWidgetNav(INavigation widgetNav);
}
