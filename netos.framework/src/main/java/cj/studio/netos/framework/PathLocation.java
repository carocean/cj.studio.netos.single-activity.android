package cj.studio.netos.framework;

import android.util.Log;

class PathLocation implements ILocation {
    INavigation moduleNav;
    INavigation regionNav;
    INavigation widgetNav;
    ISelection selection;

    @Override
    public void setSelection(ISelection selection) {
        this.selection = selection;
    }

    @Override
    public void setModuleNav(INavigation moduleNav) {
        this.moduleNav = moduleNav;
    }

    @Override
    public void setRegionNav(INavigation regionNav) {
        this.regionNav = regionNav;
    }

    @Override
    public void setWidgetNav(INavigation widgetNav) {
        this.widgetNav = widgetNav;
    }

    @Override
    public void locate(String path) {
        Log.d("back to", path);
        if ("/".equals(path)) {
            regionNav.navigate("messager");
            return;
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (path.startsWith("/")) {
            path = path.substring(1, path.length());
        }
        String[] paths = path.split("/");
        if (paths.length == 1) {
            locateToModule(paths[0]);
        } else if (paths.length == 2) {
            if (paths[1].endsWith(".region")) {
                if (selection.selectedModule() != null && "desktop".equals(selection.selectedModule().name())) {
                    String region = path.substring("/desktop".length(), path.lastIndexOf(".region"));
                    locateToRegion(region);
                } else {
                    locateToModule("desktop");
                }
            } else {//to widget of moudle
                locateToWidget(String.format("/%s", paths[1]));
            }
        } else {//o widget of moudle ,去掉前面两节
            int pos = path.indexOf(".region");
            if (pos > 0) {
                path = path.substring(pos + ".region".length(), path.length());
            } else {
                path = "";
                for (int i = 1; i < paths.length; i++) {
                    path = String.format("/%s", paths[i]);
                }
            }
            locateToWidget(path);
        }


    }

    private void locateToWidget(String path) {
        if (widgetNav == null) return;
        widgetNav.navigate(path);
    }

    private void locateToModule(String path) {
        if (moduleNav == null) return;
        moduleNav.navigate(path);
        selection.setSelectedRegion(null);
        selection.
                setSelectedWidget(null);
    }

    private void locateToRegion(String region) {
        if (regionNav == null) return;
        String module = selection.selectedModule() == null ? "" : selection.selectedModule().name();
        if (!"desktop".equals(module) || ("desktop".equals(module) && selection.selectedWidget() != null)) {
            locateToModule("desktop");
        }
        regionNav.navigate(region);
        selection.setSelectedWidget(null);
    }
}
