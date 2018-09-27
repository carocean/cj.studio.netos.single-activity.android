package cj.studio.netos.module.desktop;

import cj.studio.netos.framework.IDesktopRegion;
import cj.studio.netos.framework.IRegionSelection;

public class RegionSelection implements IRegionSelection {
    IDesktopRegion selectRegion;


    @Override
    public IDesktopRegion selectRegion() {
        return selectRegion;
    }

    @Override
    public void setSelectRegion(IDesktopRegion region) {
        this.selectRegion=region;
    }
}
