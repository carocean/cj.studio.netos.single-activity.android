package cj.studio.netos.framework;

class Selection implements ISelection {
    IModule selectedModule;
    IWidget selectedWidget;
    IDesktopRegion selectedRegion;
    IViewport currentViewport;
    public Selection(IServiceProvider site) {
    }
    @Override
    public IViewport getCurrentViewport() {
        return currentViewport;
    }
    @Override
    public void setCurrentViewport(IViewport currentViewport) {
        this.currentViewport = currentViewport;
    }

    @Override
    public IModule selectedModule() {
        return selectedModule;
    }
    @Override
    public void setSelectedModule(IModule selectedModule) {
        this.selectedModule = selectedModule;
    }
    @Override
    public IDesktopRegion selectedRegion() {
        return selectedRegion;
    }
    @Override
    public void setSelectedRegion(IDesktopRegion selectedRegion) {
        this.selectedRegion = selectedRegion;
    }

    @Override
    public IWidget selectedWidget() {
        return selectedWidget;
    }

    @Override
    public void setSelectedWidget(IWidget widget) {
    this.selectedWidget =widget;
    }
}
