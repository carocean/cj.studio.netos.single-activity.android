package cj.studio.netos.framework;

class Selection implements ISelection {
    IModule selectedModule;
    public Selection(IServiceProvider site) {
    }

    @Override
    public IModule selectedModule() {
        return selectedModule;
    }
    @Override
    public void setSelectedModule(IModule selectedModule) {
        this.selectedModule = selectedModule;
    }
}
